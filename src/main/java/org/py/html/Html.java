package org.py.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Html extends AbstractHtmlParser {
    public static Document get(String url) throws IOException {
        if(!url.startsWith("http://"))
            url = "http://" + url;
        return Jsoup.connect(url).get();
    }

    public Html() {
    }

    public Html(Document document) {
        if(null == document)
            throw new IllegalArgumentException("非法的参数，document不能为空！");
        this.document = document;
    }

    @Override
    public String toString() {
        return document.html();
    }

}
