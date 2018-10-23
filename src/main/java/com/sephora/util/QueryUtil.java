package com.sephora.util;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.Map;

public class QueryUtil {

    private String index;
    private int size;
    private TransportClient client = ESUtil.getClient();
    private SearchHits searchHits;

    public QueryUtil(String index, int size) {
        this.index = index;
        this.size = size;
    }

    public QueryUtil query(QueryBuilder queryBuilder){
        SearchResponse searchResponse = client.prepareSearch(index).setQuery(queryBuilder).setSize(size).get();
        this.searchHits = searchResponse.getHits();
        return this;
    }

    public void print(){
        if(searchHits==null){
            return ;
        }
        for(SearchHit searchHit:searchHits){
            System.out.println(searchHit.getSourceAsString());
            System.out.println(searchHit.getIndex());
            System.out.println(searchHit.getType());
            System.out.println(searchHit.getId());


            Map<String,Object> map = searchHit.getSourceAsMap();
            for(String key:map.keySet()){
                System.out.println(key +": "+ map.get(key));
            }
        }
    }


}
