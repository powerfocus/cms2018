package org.py.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统安装程序
 */
public class Setup {
    private static final String DEFAULTLIST = ".defaultlist";
    private FilesUtil filesUtil;
    private List<String> defaultlist() throws IOException {
        return filesUtil.readAllLines(filesUtil.to(DEFAULTLIST));
    }

    /**
     * 创建默认系统文件列表文件，用于检查系统文件是否被更改或在删除文件时验证是否为受保护的系统文件；
     * 在系统使用过程中，不能由用户创建此文件，否则验证功能很可能将失效！
     * @throws IOException
     */
    private void initializeDefaultList() throws IOException {
        Path path = filesUtil.to(DEFAULTLIST);
        List<String> lines = new ArrayList<>();
        filesUtil.list(filesUtil.getRoot()).forEach(it -> lines.add(filesUtil.relative(it).toString()));
        filesUtil.write(path, lines);
    }
    private void reInitializeDefaultList() throws IOException {
        Path path = filesUtil.to(DEFAULTLIST);
        if(!filesUtil.exists(path) || filesUtil.read(path, "").matches("\\s*"))
            initializeDefaultList();
    }
    public Setup(FilesUtil filesUtil) {
        this.filesUtil = filesUtil;
    }
    public void install() throws IOException {
        reInitializeDefaultList();
    }
    public List<String> readDefaultList() throws IOException {
        return defaultlist();
    }
}
