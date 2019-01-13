package org.py.html;

import org.jsoup.nodes.Document;

public abstract class AbstractHtmlBase {
    protected Document document;

    public AbstractHtmlBase() {
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

}
