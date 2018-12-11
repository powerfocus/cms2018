package org.py.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Path path = childlist.get(FilesUtil.FILES).get(0);
        Files.readAllLines(path).forEach(System.out::println);
    }
    @Test
    public void relative() throws IOException {
        Path root = util.getRoot();
        Map<String, List<Path>> childlist = util.childlist(Paths.get(root.toRealPath().toString(), "admin"));
        List<Path> files = childlist.get(FilesUtil.FILES);
        Path path = files.get(0);
        System.out.println(root.toRealPath());
        System.out.println("原路径：" + path);
        System.out.println("相对路径：" + util.relative(path));
    }
}