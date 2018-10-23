package com.sephora.documentmanagement;

import com.sephora.util.ESUtil;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class UpdateDocDemo {
    public static void main(String[] args) throws IOException {
        TransportClient client = ESUtil.getClient();
        UpdateRequest request = new UpdateRequest();
        request.index("index1").type("blog").id("2").doc(jsonBuilder().startObject().field("title","更新文档2").endObject());

        UpdateResponse response = client.update(request).actionGet();
        System.out.println(response.status());
        System.out.println(response.getIndex());
        System.out.println(response.getVersion());
        System.out.println(response.getId());
    }
}
