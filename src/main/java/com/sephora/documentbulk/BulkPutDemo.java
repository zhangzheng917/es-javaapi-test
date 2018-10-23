package com.sephora.documentbulk;

import com.sephora.util.ESUtil;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class BulkPutDemo {
    public static void main(String[] args) throws IOException {
        TransportClient client = ESUtil.getClient();

        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        bulkRequestBuilder.add(client.prepareIndex("twitter","tweet","1").
                setSource(jsonBuilder().startObject().field("user", "kimchy")
                                .field("postDate", new Date())
                                .field("message", "trying out Elasticsearch")
                                .endObject()));

        bulkRequestBuilder.add(client.prepareIndex("twitter","tweet","2").
                setSource(jsonBuilder().startObject().field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post").endObject()));

        BulkResponse bulkResponse = bulkRequestBuilder.get();
        System.out.println(bulkResponse.status());
        if (bulkResponse.hasFailures()){
            System.out.println("批量插入失败");
        }


    }
}
