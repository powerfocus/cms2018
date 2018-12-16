package org.py.util;

import ch.qos.logback.core.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileuploadUtilTest {
    @Autowired
    private FileUtil futil;
    @Test
    public void test() {

    }
}