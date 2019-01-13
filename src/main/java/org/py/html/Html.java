package org.py.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Html extends AbstractHtmlParser {
    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    public static Document get(String url) throws IOException {
        if(!url.startsWith("https://") && !url.startsWith("http://"))
            url = HTTP.concat(url);
        Document document = Jsoup.connect(url).get();
        return document;
    }

    public Html() { }

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
