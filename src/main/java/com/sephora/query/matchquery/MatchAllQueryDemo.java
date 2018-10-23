package com.sephora.query.matchquery;

import com.sephora.util.ESUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.Map;

public class MatchAllQueryDemo {
    public static void main(String[] args) {
        TransportClient client = ESUtil.getClient();
        //构建查询对象
        QueryBuilder query = QueryBuilders.matchAllQuery();
        //将结果存入SearchResponse
        String indexName = "website";
        if(ESUtil.isIndexExists(indexName)){
            SearchResponse searchResponse = client.prepareSearch(indexName).
                    setQuery(query).setSize(10).get();

            SearchHits searchHits = searchResponse.getHits();
            for(SearchHit searchHit: searchHits){
                System.out.println(searchHit.getSourceAsString());
                System.out.println(searchHit.getIndex());
                System.out.println(searchHit.getType());
                System.out.println(searchHit.getId());

                Map<String,Object> map = searchHit.getSourceAsMap();
                for(String key: map.keySet()){
                    System.out.println(key+": "+map.get(key));
                }
            }
        }else {
            System.out.println("该索引不存在！");
        }
    }
}
