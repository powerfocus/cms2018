package org.py.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.py.html.Html;
import org.py.html.UrlSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlSaveTest {
    @Autowired
    private UrlSave urlSave;
    @Test
    public void save() throws IOException {
        String savepath = "C:\\Users\\Administrator\\Desktop\\files";
        urlSave.getRemoteFile("http://www.sohu.com", savepath);
    }
    @Test
    public void save2() throws IOException {
        HttpConnection connect = (HttpConnection) Jsoup.connect("http://www.sohu.com");
        connect.timeout(1000);
        Document document = connect.get();
        Elements srcs = document.getElementsByAttribute("src");
        String domain = document.baseUri().endsWith("/") ? document.baseUri() : document.baseUri().concat("/");
        String savepath = "C:\\Users\\Administrator\\Desktop\\files";
        System.out.println("domain: " + domain);
        srcs.stream().map(src -> src.attr("src")).collect(Collectors.toList())
                .forEach(src -> {
                    try {
                        if(src.startsWith("//"))
                            src = src.substring(2);
                        if(!src.startsWith("http://") && !src.startsWith("https://"))
                            src = domain.concat(src);
                        String ext = FilenameUtils.getExtension(src);
                        String fn = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
                        if(!ext.isEmpty()) {
                            URL url = new URL(src);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            if(httpURLConnection.getResponseMessage().equals("OK")) {
                                File file = new File(savepath + File.separator + fn + "." + ext);
                                FileUtils.copyURLToFile(url, file);
                                String content = FileUtils.readFileToString(file);
                                if(content.contains("404") || content.contains("405") || content.contains("502")) {
                                    FileUtils.deleteQuietly(file);
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                });
    }
    @Test
    public void save4() {
        Connection connect = Jsoup.connect("www.sohu.com");

    }
    @Test
    public void u() throws IOException {
        URL url = new URL("http://29e5534ea20a8.cdn.sohucs.com/c_zoom,h_213/c_cut,x_0,y_1,w_550,h_366/os/news/75a213428f57905a4911107fac8a470d.jpg");
        String savepath = "C:\\Users\\Administrator\\Desktop\\files";
        FileUtils.copyURLToFile(url, new File(savepath + File.separator + "75a213428f57905a4911107fac8a470d.jpg"));
    }
}