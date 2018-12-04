package org.py.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.py.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleMapperTest {
    @Autowired
    private ArticleMapper mapper;
    @Test
    public void selectall() {
        mapper.selectAll().forEach(System.out::println);
    }
    @Test
    public void save() {
        Article article = Article.builder()
                .title("admin")
                .author("admin")
                .shorttitle("admin")
                .source("inter net")
                .isvarify(true)
                .publishdatetime(LocalDateTime.now())
                .content("这是一篇测试文章2018.11.30")
                .build();
        int re = mapper.insert(article);
        System.out.println(re + "条数据被保存！");
    }
    @Test
    public void update() {
        Article article = mapper.selectByPrimaryKey(1);
        article.setTitle("修改");
        article.setIsvarify(false);
        int re = mapper.updateByPrimaryKey(article);
        System.out.println(re + "条数据被修改！");
    }
}