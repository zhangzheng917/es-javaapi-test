package com.sephora.query.termquery;

import com.sephora.util.QueryUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

public class TermQueryDemo {
    QueryUtil util = new QueryUtil("website",5);

    @Test
    public void testTerm(){
        QueryBuilder query = QueryBuilders.termQuery("title","vmware");
        util.query(query).print();
    }

    @Test
    public void testTerms(){
        QueryBuilder query = QueryBuilders.termsQuery("title","vmware","yum");
        util.query(query).print();
    }

    @Test
    public void testRange(){
        QueryBuilder query = QueryBuilders.rangeQuery("postdate").from("2017-01-01").to("2017-12-31").format("yyyy-MM-dd");
        util.query(query).print();
    }

    @Test
    public void testExist(){
        QueryBuilder query = QueryBuilders.existsQuery("url");
        util.query(query).print();
    }

    @Test
    public void testPrefix(){
        QueryBuilder query = QueryBuilders.prefixQuery("author","so");
        util.query(query).print();
    }

    @Test
    public void testWildcard(){
        QueryBuilder query = QueryBuilders.wildcardQuery("title","*yum*");
        util.query(query).print();
    }

    @Test
    public void testRegexp(){
        QueryBuilder query = QueryBuilders.regexpQuery("title","gc.*");
        util.query(query).print();
    }

    @Test
    public void testFuzzy(){
        QueryBuilder query = QueryBuilders.fuzzyQuery("title","vmwere");
        util.query(query).print();
    }

    @Test
    public void testType(){
        QueryBuilder query = QueryBuilders.typeQuery("blog");
        util.query(query).print();
    }

    @Test
    public void testIds(){
        QueryBuilder query = QueryBuilders.idsQuery().addIds("1","3","5");
        util.query(query).print();
    }

}
