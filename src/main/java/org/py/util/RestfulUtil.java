package org.py.util;

import java.io.File;

public final class RestfulUtil {
    public static final String FSSEPARATOR = File.separator;
    public static final String URISEPARATOR = "/";
    public RestfulUtil() { }
    public String processURI(String uri, String mapping) {
        return uri.replace(mapping, "").replace(FSSEPARATOR, URISEPARATOR);
    }
    public String webSeparator(String uri) {
        return uri.replace(File.separator, "/");
    }
    public String localSeparator(String uri) {
        return uri.replace("/", File.separator);
    }
    public String separatorResolver(String path) {
        return path.replace(FSSEPARATOR, URISEPARATOR);
    }
}
