package com.imooc;


import com.imooc.es.pojo.Stu;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;


    public List<Stu> getStuList() {

        List<Stu> lst = new ArrayList<>();

        Stu stu1 = new Stu();
        stu1.setStuId(1001L);
        stu1.setName("spider man 1");
        stu1.setAge(18);
        stu1.setMoney(18.8f);
        stu1.setSign("i am spider man");
        stu1.setDescription("i wish i am spider man");


        Stu stu2 = new Stu();
        stu2.setStuId(1002L);
        stu2.setName("spider man 2");
        stu2.setAge(18);
        stu2.setMoney(18.8f);
        stu2.setSign("i am spider man");
        stu2.setDescription("i wish i am spider man");


        Stu stu3 = new Stu();
        stu3.setStuId(1003L);
        stu3.setName("spider man 3");
        stu3.setAge(18);
        stu3.setMoney(18.8f);
        stu3.setSign("i am spider man");
        stu3.setDescription("i wish i am spider man");


        Stu stu4 = new Stu();
        stu4.setStuId(1004L);
        stu4.setName("spider man 4");
        stu4.setAge(18);
        stu4.setMoney(18.8f);
        stu4.setSign("i am spider man");
        stu2.setDescription("i wish i am spider man");

        Stu stu5 = new Stu();
        stu5.setStuId(1005L);
        stu5.setName("spider man 5");
        stu5.setAge(18);
        stu5.setMoney(18.8f);
        stu5.setSign("i am spider man");
        stu5.setDescription("i wish i am spider man");

        lst.add(stu1);
        lst.add(stu2);
        lst.add(stu3);
        lst.add(stu4);
        lst.add(stu5);
        return lst;
    }

    @Test
    public void createIndexStu() {

        List<Stu> stuList = getStuList();

        for (Stu stu : stuList) {

            IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
            esTemplate.index(indexQuery);
        }


    }


    @Test
    public void deleteIndexStu() {

        esTemplate.deleteIndex(Stu.class);
    }


    @Test
    public void updateIndexStu() {

        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("sign", "i am not super man");
        sourceMap.put("money", 88.6f);
        sourceMap.put("age", 33);

        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(sourceMap);
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withClass(Stu.class)
                .withId("1002")
                .withIndexRequest(indexRequest)
                .build();


        esTemplate.update(updateQuery);
    }

    //select * from stu where id=1002
    @Test
    public void getStuDoc() {

        GetQuery query = new GetQuery();
        query.setId("1002");
        Stu stu = esTemplate.queryForObject(query, Stu.class);
    }


    @Test
    public void deleteStuDOc() {

        esTemplate.delete(Stu.class, "1002");
    }

    @Test
    public void searchStuDoc() {

        Pageable pageable = PageRequest.of(0, 2);


        SearchQuery query = new NativeSearchQueryBuilder()
                //查询
                .withQuery(QueryBuilders.matchQuery("description", "wish man"))
                //排序
                .withSort(SortBuilders.fieldSort("stuId").order(SortOrder.ASC))
                //分页
                .withPageable(pageable)
                .build();
        AggregatedPage<Stu> pageStu = esTemplate.queryForPage(query, Stu.class);
        List<Stu> stuList = pageStu.getContent();

        System.out.println("total page: " + pageStu.getTotalPages());
        for (Stu s : stuList) {
            System.out.println(s);
        }

    }


    @Test
    public void highlighStuDoc() {

        Pageable pageable = PageRequest.of(0, 2);

        String preTag = "<font color='red'>";
        String postTag = "</font>";


        SearchQuery query = new NativeSearchQueryBuilder()
                //查询
                .withQuery(QueryBuilders.matchQuery("description", "wish man"))
                //排序
                .withSort(SortBuilders.fieldSort("stuId").order(SortOrder.ASC))
                //高亮显示
                .withHighlightFields(new HighlightBuilder.Field("description")
                        .preTags(preTag).postTags(postTag))
                //分页
                .withPageable(pageable)
                .build();
        AggregatedPage<Stu> pageStu = esTemplate.queryForPage(query, Stu.class);
        List<Stu> stuList = pageStu.getContent();

        System.out.println("total page: " + pageStu.getTotalPages());
        for (Stu s : stuList) {
            System.out.println(s);
        }

    }
}
