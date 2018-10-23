package com.sephora.query.boolquery;

import com.sephora.util.QueryUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.junit.Test;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.exponentialDecayFunction;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.randomFunction;

public class BoolQueryDemo {
    QueryUtil util = new QueryUtil("website",5);

    @Test
    public void testBool() throws Exception{
        QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("title","centos")).
                should(QueryBuilders.termQuery("title","yum")).filter(QueryBuilders.termsQuery("title","gcc"));
        util.query(query).print();

    }

    @Test
    public void testDisMax(){
        QueryBuilder query = QueryBuilders.disMaxQuery().add(QueryBuilders.termQuery("title","centos")).
                add(QueryBuilders.termQuery("title","yum")).tieBreaker(0.7f).boost(1.2f);
        util.query(query).print();
    }

    @Test
    public void testConstantScore(){
        QueryBuilder query = QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("title","centos")).boost(0.2f);
        util.query(query).print();
    }


    /*
    后面两个没测试
     */
    @Test
    public void testFunctionScore(){
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] functions = {
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        matchQuery("name", "kimchy"),
                        randomFunction()),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        exponentialDecayFunction("age", 0L, 1L))
        };
        QueryBuilder query = QueryBuilders.functionScoreQuery(functions);
        util.query(query).print();
    }

    @Test
    public void testBoost(){
        QueryBuilder query = QueryBuilders.boostingQuery(
                QueryBuilders.termQuery("name","kimchy"),
                QueryBuilders.termQuery("name","dadoonet"))
                .negativeBoost(0.2f);
        util.query(query).print();
    }
}
