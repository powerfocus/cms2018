package org.py.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilesUtilTest {
    @Autowired
    private FilesUtil util;
    @Test
    public void test() throws IOException {
        Map<String, List<Path>> childlist = util.childlist(util.getRoot());
        childlist.get(FilesUtil.DIRS).forEach(it -> {
            System.out.println(it.getFileName());
        });
        System.out.println("====================");
        childlist.get(FilesUtil.FILES).forEach(it -> {
            System.out.println(it.getFileName());
        });
    }
}