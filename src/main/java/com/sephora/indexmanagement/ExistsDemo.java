package com.sephora.indexmanagement;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ExistsDemo {
    public static void main(String[] args) throws UnknownHostException {
        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name","my-application").build();
        //创建client对象
        TransportClient transportClient = new PreBuiltTransportClient(settings).
                addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9300));
        //获取IndicesAdminClient对象
        IndicesAdminClient indicesAdminClient = transportClient.admin().indices();
        //判断索引是否存在
        IndicesExistsResponse indicesExistsResponse = indicesAdminClient.prepareExists("fruit").get();
        System.out.println(indicesExistsResponse.isExists());
    }
}
