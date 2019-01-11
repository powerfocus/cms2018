package org.py.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractHtmlParser extends AbstractHtmlBase {
    protected String regexSrc = "src=[\'\"]([^\'\"<%{]+)[\'\"]";

    public AbstractHtmlParser() { }

    public void parse() {
        if(null == document)
            throw new IllegalArgumentException("必须初始化document对象！");
        Matcher matcher = Pattern.compile(regexSrc).matcher(document.html());
        while(matcher.find())
            srclist.add(matcher.group(1));
    }
}
