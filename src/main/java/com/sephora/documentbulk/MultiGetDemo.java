package com.sephora.documentbulk;

import com.sephora.util.ESUtil;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;

public class MultiGetDemo {

    public static void main(String[] args) {
        TransportClient client = ESUtil.getClient();

        MultiGetResponse multiGetResponses = client.prepareMultiGet().add("index1","blog","1","2").
                add("website","blog","1","2","2").get();

        for(MultiGetItemResponse rs : multiGetResponses){
            GetResponse response = rs.getResponse();
            if(response!=null && response.isExists()){
                System.out.println("group");
                System.out.println(response.getSourceAsString());
            }
        }
    }
}
