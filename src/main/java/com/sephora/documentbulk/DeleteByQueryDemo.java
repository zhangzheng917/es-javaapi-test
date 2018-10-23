package com.sephora.documentbulk;

import com.sephora.util.ESUtil;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;

public class DeleteByQueryDemo {
    public static void main(String[] args) {
        TransportClient client = ESUtil.getClient();

        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client).
                filter(QueryBuilders.matchQuery("title","模式")).source("index1").get();

        System.out.println(response.getDeleted());

    }
}
