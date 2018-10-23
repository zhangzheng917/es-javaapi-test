package com.sephora.util;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class ESUtil {

    private static final String CLUSTER_NAME = "my-application";
    private static final String HOSTNAME = "localhost";
    private static final int TCP_PORT = 9300;

    private static final Settings SETTINGS = Settings.builder().put("cluster.name", CLUSTER_NAME).build();

    private static volatile TransportClient TRANSPORT_CLIENT = null;


    /*
    获取TransportClient
     */
    public static TransportClient getClient(){
        if(TRANSPORT_CLIENT == null){
            synchronized (TransportClient.class){
                if(TRANSPORT_CLIENT == null){
                    try{
                        TRANSPORT_CLIENT = new PreBuiltTransportClient(SETTINGS).
                                addTransportAddress(new TransportAddress(InetAddress.getByName(HOSTNAME),TCP_PORT));
                    }catch (UnknownHostException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return TRANSPORT_CLIENT;
    }

    //获取索引管理对象
    public static IndicesAdminClient getAdminClient(){
        return getClient().admin().indices();
    }

    //判断索引是否存在
    public static boolean isIndexExists(String indexName){
        IndicesExistsResponse indicesExistsResponse = getAdminClient().prepareExists(indexName).get();
        return indicesExistsResponse.isExists();
    }

    //创建索引
    public static boolean createIndex(String indexName){
        CreateIndexResponse createIndexResponse = getAdminClient().prepareCreate(indexName.toLowerCase()).get();
        return createIndexResponse.isAcknowledged();
    }

    //创建设置分片和副本的索引
    public static boolean createIndex(String indexName, int shards, int replicas){
        Settings settings = Settings.builder().put("index.number_of_shards",shards).
                put("index.number_of_replicas",replicas).build();
        CreateIndexResponse createIndexResponse = getAdminClient().prepareCreate(indexName.toLowerCase()).setSettings(settings).execute().actionGet();
        return createIndexResponse.isAcknowledged();
    }


//todo 这个有问题
    //为索引设置mapping
    public static void setMapping(String indexName, String typeName, String mapping){
        getAdminClient().preparePutMapping(indexName).setType(typeName).setSource(mapping, XContentType.JSON).get();
    }

    //删除索引
    public static boolean deleteIndex(String indexName){
        DeleteIndexResponse deleteIndexResponse = getAdminClient().prepareDelete(indexName.toLowerCase()).execute().actionGet();
        return deleteIndexResponse.isAcknowledged();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(isIndexExists("index1"));
        System.out.println(deleteIndex("index1"));
        System.out.println(createIndex("index1",3,0));
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject("id")
                .field("type", "long")
                .endObject()
                .startObject("title")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word")
                .field("boost", 2)
                .endObject()
                .startObject("content")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .field("search_analyzer", "ik_max_word")
                .endObject()
                .startObject("postdate")
                .field("type", "date")
                .field("format", "yyyy-MM-dd HH:mm:ss")
                .endObject()
                .startObject("url")
                .field("type", "keyword")
                .endObject()
                .endObject()
                .endObject();
        System.out.println(builder.toString());
        setMapping("index1","blog",builder.toString());
    }

}
