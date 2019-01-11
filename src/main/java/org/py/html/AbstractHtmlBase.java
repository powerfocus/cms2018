package org.py.html;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHtmlBase {
    protected Document document;
    protected List<String> srclist;

    public AbstractHtmlBase() {
        srclist = new ArrayList<>();
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public List<String> getSrclist() {
        return srclist;
    }

}
