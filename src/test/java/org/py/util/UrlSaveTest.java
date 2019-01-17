package org.py.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.py.html.UrlSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlSaveTest {
    @Autowired
    private UrlSave urlSave;
    @Test
    public void save() throws IOException {
        String savepath = "C:\\Users\\Administrator\\Desktop\\files";
        Map<String, String> re = urlSave.getRemoteFile("https://www.163.com", savepath);
        System.out.println("====================");
        re.forEach((k, v) -> System.out.println(k + " -> " + v));
    }
}