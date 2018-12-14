package org.py.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FilesUtil {
    private String rootpath;
    private Path root;
    public static final String DIRS = "dirlist";
    public static final String FILES = "filelist";

    public FilesUtil(String rootpath) throws IOException {
        ClassPathResource resource = new ClassPathResource(rootpath);
        root = Paths.get(resource.getFile().getAbsolutePath());
    }

    /**
     * 将路径转换为相对路径
     * @param path 路径参数
     * @return 相对路径
     */
    public Path relative(Path path) {
        return root.relativize(path);
    }

    /**
     * 跳转到指定路径
     * @param path 目标路径
     * @return 目标路径
     */
    public Path to(Path path) {
        return Paths.get(root.toString(), path.toString());
    }

    /**
     * 跳转到指定路径
     * @param path 目标路径
     * @return 目标路径
     */
    public Path to(String path) {
        return Paths.get(root.toString(), path);
    }

    /**
     * 会到上级目录
     * @param path
     * @return 上级目录路径
     */
    public Path up(Path path) {
        return path.getParent();
    }

    /**
     * 获得指定路径中的文件扩展名
     * @param path 路径
     * @return 文件扩展名
     */
    public String extensionName(String path) {
        return path.substring(path.lastIndexOf("."));
    }

    public Map<String, List<Path>> childlist(Path dir) throws IOException {
        Map<String, List<Path>> map = new LinkedHashMap<>();
        List<Path> dirlist = new ArrayList<>();
        List<Path> filelist = new ArrayList<>();
        if(null != dir && dir.isAbsolute()) {
            Files.list(dir)
                    .forEach(it -> {
                        if(Files.isDirectory(it))
                            dirlist.add(it);
                        else
                            filelist.add(it);
                    });
            map.put(DIRS, dirlist);
            map.put(FILES, filelist);
        }
        return map;
    }

    public List<String> readText(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    public void writeText(Path path, List<String> lines) throws IOException {
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    public String getRootpath() {
        return rootpath;
    }

    public Path getRoot() {
        return root;
    }

}
