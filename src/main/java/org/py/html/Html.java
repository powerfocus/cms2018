package org.py.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Html extends AbstractHtmlParser {
    public static Document get(String url) throws IOException {
        if(!url.startsWith(HTTP) && !url.startsWith(HTTPS))
            url = HTTP.concat(url);
        Document document = Jsoup.connect(url).get();
        return document;
    }

    public Html() { }

    public Html(Document document) {
        Assert.notNull(document, "参数错误，Document对象不能为空！");
        this.document = document;
    }

    /**
     * 解析html文档中所有 src 的值
     * @return
     * 所有src的列表
     */
    public List<String> parseSrc() {
        Assert.notNull(document, "Document对象不能为空！");
        String htmldomain = document.baseUri();
        String pruedomain = htmldomain.replace(HTTP, "").replace(HTTPS, "");
        String protocol = htmldomain.replace(pruedomain, "");
        return document.getElementsByAttribute("src").stream()
                .map(src -> src.attr("src"))
                .map(src -> src.startsWith("//") ? src.substring(src.indexOf("//") + 2) : src)
                .map(src -> src.startsWith(HTTP) || src.startsWith(HTTPS) ? src : src.startsWith(pruedomain) ? protocol + src : htmldomain + "/" + src)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return document.html();
    }

}
