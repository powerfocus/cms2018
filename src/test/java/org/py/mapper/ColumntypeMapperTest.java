package org.py.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ColumntypeMapperTest {
    @Autowired
    private ColumntypeMapper mapper;
    @Test
    public void test() {
        mapper.selectAll().forEach(System.out::println);
    }
}