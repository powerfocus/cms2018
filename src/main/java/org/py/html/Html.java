package org.py.html;

import org.jsoup.nodes.Document;

public class Html extends AbstractHtmlParser {
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
