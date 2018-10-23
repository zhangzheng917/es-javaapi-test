package com.sephora.documentbulk;

import com.sephora.util.ESUtil;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

public class BulkProcessorDemo {

    public static void main(String[] args) {
        TransportClient client = ESUtil.getClient();

        BulkProcessor processor = BulkProcessor.builder(
                client, new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long l, BulkRequest bulkRequest) {
                        System.out.println("请求数: " + bulkRequest.numberOfActions());
                    }

                    @Override
                    public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                        if(!bulkResponse.hasFailures()){
                            System.out.println("执行成功");
                        }else{
                            System.out.println("执行失败");
                        }

                    }

                    @Override
                    public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                        System.out.println(throwable);
                    }
                }
        ).setBulkActions(1000).setConcurrentRequests(1).setFlushInterval(TimeValue.timeValueSeconds(5)).setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB)).
                setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();

        processor.add(new DeleteRequest("twitter","tweet","1"));
        processor.add(new DeleteRequest("twitter","tweet","2"));
        processor.flush();
        processor.close();

        client.admin().indices().prepareRefresh().get();
        client.prepareSearch().get();
    }
}
