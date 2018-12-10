package org.py.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilesUtilTest {
    @Autowired
    private FilesUtil util;
    @Test
    public void test() {
        util.getDirlist().forEach(it -> System.out.println("[" + it.getFileName() + "]"));
        util.getFilelist().forEach(it -> System.out.println(it.getFileName()));
    }
}