package org.py.html;

import org.jsoup.nodes.Document;

public abstract class AbstractHtmlBase {
    protected Document document;
    public static final String WWW = "www.";
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";
    public static final String HOSTSEPARATOR = "//";
    public static final String SEPARATOR = "/";

    public AbstractHtmlBase() {
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

}
