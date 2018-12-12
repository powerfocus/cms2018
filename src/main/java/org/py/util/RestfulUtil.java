package org.py.util;

import java.io.File;

public final class RestfulUtil {
    public static final String FSSEPARATOR = File.separator;
    public static final String URISEPARATOR = "/";
    public RestfulUtil() { }
    public String processURI(String uri, String mapping) {
        return uri.replace(mapping, "").replace(FSSEPARATOR, URISEPARATOR);
    }
}
