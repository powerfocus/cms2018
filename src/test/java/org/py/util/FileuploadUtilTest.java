package org.py.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileuploadUtilTest {
    @Autowired
    private FilesUtil filesUtil;
    @Test
    public void list() throws IOException {
        filesUtil.list(filesUtil.getRoot()).forEach(System.out::println);
    }
    @Test
    public void test() throws IOException {
        String filename = ".defaultlist";
        Path path = Paths.get(filesUtil.getRoot().toString(), filename);
        List<String> lines = new ArrayList<>();
        filesUtil.list(filesUtil.getRoot()).forEach(it -> lines.add(filesUtil.relative(it).toString()));
        Files.write(path, lines, StandardCharsets.UTF_8);
        System.out.println(path.toString());
        System.out.println(lines);
    }
}