package org.py.util;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * 文件系统操作工具类
 */
public class FilesUtil {
    private String rootpath;
    private Path root;
    public static final String DIRS = "dirlist";
    public static final String FILES = "filelist";

    /**
     * 判断目录访问知否在可反问的目录范围内，默认访问的目录路径是classpath:public
     * @param path 目标路径
     * @return 是否在可访问路径范围内
     */
    private boolean checkPath(Path path) {
        return path.startsWith(root);
    }

    /**
     * 判断目录是否在可访问范围内，接受一个字符串参数
     * @param path 目标路径
     * @return 是否在可访问路径范围内
     */
    private boolean checkPath(String path) {
        return Paths.get(path).startsWith(root);
    }

    public FilesUtil(String rootpath) throws IOException {
        ClassPathResource resource = new ClassPathResource(rootpath);
        root = Paths.get(resource.getFile().getAbsolutePath());
    }

    public String w(String uri) {
        return uri.replace(File.separator, "/");
    }

    public String l(String uri) {
        return uri.replace("/", File.separator);
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

    public Path to(String... paths) {
        return Paths.get(root.toString(), paths);
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
     * 判断路径表示的文件系统对象是否存在
     * @param path
     * @return 路径是否存在
     */
    public boolean exists(Path path) throws IllegalArgumentException {
        if(checkPath(path)) throw new IllegalArgumentException("目录访问超过权限！");
        return Files.exists(path);
    }

    public boolean exists(String path) {
        return Files.exists(Paths.get(root.toString(), path));
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

    /**
     * 删除目录及其子目录
     * @param target 目标目录
     * @throws IOException
     */
    public Path deltree(Path target) throws IOException {
        return Files.walkFileTree(target, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
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
