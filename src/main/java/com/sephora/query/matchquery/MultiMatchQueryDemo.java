package com.sephora.query.matchquery;

import com.sephora.util.QueryUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class MultiMatchQueryDemo {
    public static void main(String[] args) {
        QueryUtil util = new QueryUtil("website",3);

        QueryBuilder query = QueryBuilders.multiMatchQuery("centos","title","abstract");

        util.query(query).print();
    }
}
