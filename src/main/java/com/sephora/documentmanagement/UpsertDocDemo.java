package com.sephora.documentmanagement;

import com.sephora.util.ESUtil;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class UpsertDocDemo {

    public static void main(String[] args) throws IOException {
        TransportClient client = ESUtil.getClient();
        IndexRequest request1 = new IndexRequest("index1","blog","1").source(jsonBuilder().startObject()
                        .field("id","1")
                        .field("title","装饰模式")
                        .field("content","动态地扩展一个对象的功能")
                        .field("postdate","2018-02-03 14:38:10")
                        .field("url","csdn.net/79239072")
                        .endObject()
                        );

        UpdateRequest request2 = new UpdateRequest("index1","blog","1").
                doc(jsonBuilder().startObject().field("title","装饰模式解读").endObject()).upsert(request1);

        UpdateResponse response = client.update(request2).actionGet();
        System.out.println(response.status());
        System.out.println(response.getType());
        System.out.println(response.getId());
        System.out.println(response.getVersion());
    }
}
