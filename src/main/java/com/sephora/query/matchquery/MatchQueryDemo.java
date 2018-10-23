package com.sephora.query.matchquery;

import com.sephora.util.QueryUtil;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;


public class MatchQueryDemo {
    public static void main(String[] args) {
        QueryUtil util = new QueryUtil("website",5);
        QueryBuilder query = QueryBuilders.matchQuery("title","centos");

        util.query(query).print();
    }

    @Test
    public void testOperate(){
        QueryUtil util = new QueryUtil("website",5);
        QueryBuilder query = QueryBuilders.matchQuery("title","CentOS升级").operator(Operator.AND);

        util.query(query).print();
    }
}
