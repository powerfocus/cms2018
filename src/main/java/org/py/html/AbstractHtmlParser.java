package org.py.html;

import org.jsoup.nodes.Document;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
/**
 *
 * @author pythagoras
 * @version v 0.1 2019/1/17 11:02 pythagoras
 */
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

    /**
     * 获得html文档中的资源url
     */
    public List<String> parse() {
        Assert.notNull(document, "必须初始化document对象！");
        List<String> srclist = new ArrayList<>();
        Matcher matcher = Pattern.compile(REGEXSRC).matcher(document.html());
        while(matcher.find()) {
            String group = matcher.group(1);
            group = processURL(group);
            if(group.matches(REGEXURL))
                srclist.add(group);
        }
        return srclist;
    }

    /**
     * 使用 jsoup 获取html中的src属性url
     * @param document
     * @return
     *  src属性中url的列表
     */
    public List<String> parseSrc(Document document) {
        return document.getElementsByAttribute("src")
                .stream()
                .map(src -> src.attr("src"))
                .collect(Collectors.toList());
    }
}
