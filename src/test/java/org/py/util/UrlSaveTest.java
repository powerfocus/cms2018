package org.py.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Path;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlSaveTest {
    @Autowired
    private UrlSave urlSave;
    @Test
    public void test() throws IOException {
        Document document = Jsoup.connect("http://www.163.com").get();
        System.out.println(document.html());
    }
    @Test
    public void save() throws IOException {
        Path save = urlSave.save("http://www.163.com");
        System.out.println("保存路径：" + save);
        System.out.println("路径是否存在：" + urlSave.getFilesUtil().exists(save));
    }
    @Test
    public void f() {
        String fn = urlSave.getFilesUtil().extensionName("http://www.163.com/abc.txt");
        System.out.println(fn);
    }
}