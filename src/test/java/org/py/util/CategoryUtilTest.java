package org.py.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.py.model.Columntype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryUtilTest {
    @Autowired
    private CategoryUtil util;
    @Test
    public void test() {
        StringBuffer strbuffer = new StringBuffer();
        util.tree(0, 0,strbuffer);
        System.out.println(strbuffer.toString());
    }
    @Test
    public void test2() {
        List<Map<String, Columntype>> list = new ArrayList<>();
        util.treeList(0, 0, list);
        list.forEach(it -> {
            it.forEach((k, v) -> {
                System.out.print(k);
                System.out.println(v.getTypename());
            });
        });
    }
}