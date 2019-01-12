package org.py.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.py.html.UrlSave;
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
    public void saveRemote() {
        String savepath = "C:\\Users\\Administrator\\Desktop\\files";
        String re = urlSave.getRemoteFile("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1202533505,1003824237&fm=173&app=25&f=JPEG?w=640&h=610&s=10197093464376FCCCAC00CF0300B022", savepath);
        System.out.println("文件以保存到：" + re);
    }
    @Test
    public void filetype() {
        String urlstr = "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1202533505,1003824237&fm=173&app=25&f=JPEG?w=640&h=610&s=10197093464376FCCCAC00CF0300B022";
        String filetype = urlSave.filetype(urlstr);
        System.out.println(filetype);
    }
    @Test
    public void f() {
        String fn = urlSave.getFilesUtil().extensionName("http://www.163.com/abc.txt");
        System.out.println(fn);
    }
}