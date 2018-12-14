package org.py.explorer;

import java.nio.file.Path;
import java.util.List;

/**
 * 资源处理程序
 * write() 资源内容写入
 * read() 资源内容读取
 * path() 返回资源路径
 * length() 判断资源长度
 * exists() 资源是否存在
 */
public interface Resource {
    void write(CharSequence data);
    void write(byte[] data);
    List<String> readLines(Path path);
    byte[] readBytes(Path path);
    Path path();
    long length();
    boolean exists();
}
