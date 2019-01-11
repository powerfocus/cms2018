package org.py.html;

import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HtmlTest {
    @Test
    public void test() throws IOException {
        Html html = new Html(Jsoup.connect("http://www.163.com").get());
        html.parse();
        html.getSrclist().forEach(System.out::println);
    }
}