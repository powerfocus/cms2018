package org.py.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractHtmlParser extends AbstractHtmlBase {
    protected final String REGEXSRC = "src=[\'\"]([^\'\"<%{]+)[\'\"]";
    protected final String REGEXURL = "(((ht|f)tps):\\/\\/)?[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-\\.,@?^=%&:\\/~\\+#]*[\\w\\-\\@?^=%&\\/~\\+#])?";

    protected String processURL(String url) {
        url = url.startsWith("//") ? url.replace("//", "") : url;
        url = url.contains("?") ? url.substring(0, url.indexOf("?") - 1) : url;
        return url;
    }

    public AbstractHtmlParser() { }

    public String convertUrlStr(String url) {
        return url.startsWith("http://") || url.startsWith("https://") ? url : "http://" + url;
    }

    public void parse() {
        if(null == document)
            throw new IllegalArgumentException("必须初始化document对象！");
        srclist.clear();
        Matcher matcher = Pattern.compile(REGEXSRC).matcher(document.html());
        while(matcher.find()) {
            String group = matcher.group(1);
            group = processURL(group);
            if(group.matches(REGEXURL))
                srclist.add(group);
        }
    }
}
