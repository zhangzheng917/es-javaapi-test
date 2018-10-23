package com.sephora.documentmanagement;

import com.sephora.util.ESUtil;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;

public class DelDocDemo {
    public static void main(String[] args) {
        TransportClient client = ESUtil.getClient();
        DeleteResponse deleteResponse = client.prepareDelete("index1","blog","1").get();
        System.out.println(deleteResponse.status());
        System.out.println(deleteResponse.getType());
        System.out.println(deleteResponse.getId());
        System.out.println(deleteResponse.getVersion());
    }
}
