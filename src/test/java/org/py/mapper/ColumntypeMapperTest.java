package org.py.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.py.model.Columntype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.SqlsCriteria;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ColumntypeMapperTest {
    @Autowired
    private ColumntypeMapper mapper;
    @Test
    public void test() {
        mapper.selectAll().forEach(System.out::println);
    }
    @Test
    public void update() {
        Columntype col = mapper.selectByPrimaryKey(1);
        System.out.println(col);
        col.setTypename("编程");
        col.setCol_describe("编程栏目");
        int re = mapper.updateByPrimaryKey(col);
        System.out.println(re + "条数据被更新！");
    }
    @Test
    public void selectByExample() {
        Example example = new Example(Columntype.class);
        example.createCriteria().andEqualTo("pid", 2);
        mapper.selectByExample(example).forEach(System.out::println);
    }
    @Test
    public void selectByExample2() {
        Example example = Example.builder(Columntype.class)
                .where(Sqls.custom().andEqualTo("pid", 2)).build();
        mapper.selectByExample(example).forEach(System.out::println);
    }
}