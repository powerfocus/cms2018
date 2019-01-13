package org.py.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.Assert;

import java.io.IOException;

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

    @Override
    public String toString() {
        return document.html();
    }

}
