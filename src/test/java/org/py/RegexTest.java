package org.py;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RegexTest {
    @Test
    public void url() {
        String url = "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1202533505,1003824237&fm=173&app=25&f=JPEG";
        String regex = "([^<>/\\\\\\|:\"\"\\*\\?]+)\\.\\w{3}$";
        Matcher matcher = Pattern.compile(regex).matcher(url);
        if(url.matches(regex)) {
            System.out.println("matching");
        } else {
            System.out.println("not matching");
        }
    }
    @Test
    public void test() throws IOException {
        String u = "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1202533505,1003824237&fm=173&app=25&f=JPEG?w=640&h=610&s=10197093464376FCCCAC00CF0300B022";
        URL url = new URL(u);
        String[] imgexts = {"jpg", "jpeg", "png", "bmp", "gif"};
        AtomicBoolean matching = new AtomicBoolean(false);
        AtomicReference<String> target = new AtomicReference<>("");
        Arrays.asList(imgexts).forEach(it -> {
            if(u.toLowerCase().contains(it)) {
                matching.set(true);
                target.set(it);
            }
        });
        System.out.println("匹配结果：" + target.get());
        if(matching.get())
            System.out.println("字符串匹配");
        else
            System.out.println("字符串未匹配");
        //FileUtils.copyURLToFile(url, new File("C:\\Users\\Administrator\\Desktop\\files" + File.separator + "2.jpg"));
    }
}
