package org.py.util;

import java.util.List;

public final class TemplateUtil {
    private static final String navSeparator;
    static {
        navSeparator = "<span class=\"c-999 en\">&gt;</span>";
    }
    public static String topNav(List<String> titles) {
        StringBuffer strbuffer = new StringBuffer();
        titles.forEach(title -> {
            strbuffer.append(title);
            strbuffer.append(navSeparator);
        });
        return strbuffer.toString();
    }
}
