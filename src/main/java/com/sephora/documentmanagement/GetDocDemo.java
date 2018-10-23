package com.sephora.documentmanagement;

import com.sephora.util.ESUtil;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;

public class GetDocDemo {
    public static void main(String[] args) {
        TransportClient transportClient = ESUtil.getClient();
        GetResponse getResponse = transportClient.prepareGet("index1","blog","1").get();
        if(getResponse.isExists()){
            System.out.println(getResponse.getSourceAsString());
            System.out.println(getResponse.getIndex());
            System.out.println(getResponse.getType());
            System.out.println(getResponse.getId());
            System.out.println(getResponse.getVersion());
        }else{
            System.out.println(getResponse.isExists());
        }
    }
}
