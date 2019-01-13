package org.py.html;

import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HtmlTest {
    @Test
    public void test() throws IOException {
        Document document = Html.get("baidu.com");
        System.out.println(document.baseUri());
    }
    @Test
    public void test1() throws IOException {
        Html html = new Html();
        html.setDocument(Html.get("http://www.baidu.com"));
        html.parse(true);
        html.getSrclist().forEach(System.out::println);
    }
    @Test
    public void test2() throws IOException {
        byte[] bytes = Jsoup.connect("http://www.baidu.com/img/bd_logo1.png").ignoreContentType(true).execute().bodyAsBytes();
        Path wpath = Files.write(Paths.get("C:\\Users\\Administrator\\Desktop\\bd_logo1.png"), bytes);
        System.out.println("文件已保存到：" + wpath);
    }
    @Test
    public void test3() throws IOException {
        Html html = new Html(Html.get("www.baidu.com"));
        html.parse(true);
        Path baidu = Paths.get("c:/users/administrator/desktop", "baidu");
        if(!Files.isDirectory(baidu))
            Files.createDirectory(baidu);
        html.getSrclist().forEach(src -> {
            System.out.println(src);
            String fn = FilenameUtils.getName(src);
            try {
                Path pwrite = Files.write(Paths.get(baidu.toString(), fn), Jsoup.connect(html.convertUrlStr(src)).ignoreContentType(true).execute().bodyAsBytes());
                System.out.println("文件保存到：" + pwrite);
            } catch (IOException e) {

            }
        });
    }
}