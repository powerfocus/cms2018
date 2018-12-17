package org.py.util;

import lombok.extern.java.Log;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Stream;

/**
 * 文件系统操作工具类
 */
@Log
public class FilesUtil {
    private String rootpath;
    private Path approot;
    private Path root;
    private boolean issys;
    public static final String FILESEPARATOR = File.separator;
    public static final String URISEPARATOR = "/";
    public static final String DIRS = "dirlist";
    public static final String FILES = "filelist";

    public FilesUtil(String rootpath) throws IOException {
        ClassPathResource sysresource = new ClassPathResource(".");
        ClassPathResource resource = new ClassPathResource(rootpath);
        root = Paths.get(resource.getFile().getAbsolutePath());
        approot = Paths.get(sysresource.getFile().getAbsolutePath());
    }

    /**
     * 判断目录访问知否在可反问的目录范围内，默认访问的目录路径是classpath:public
     * @param path 目标路径
     * @return 是否在可访问路径范围内
     */
    public boolean checkPath(Path path) {
        return path.startsWith(root);
    }

    /**
     * 判断目录是否在可访问范围内，接受一个字符串参数
     * @param path 目标路径
     * @return 是否在可访问路径范围内
     */
    public boolean checkPath(String path) {
        return Paths.get(path).startsWith(root);
    }

    public String w(String uri) {
        return uri.replace(FILESEPARATOR, URISEPARATOR);
    }

    public String l(String uri) {
        return uri.replace(URISEPARATOR, FILESEPARATOR);
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

    public String fileName(String path) {
        return w(Paths.get(path).getFileName().toString());
    }

    public String fileName(Path path) {
        return w(path.getFileName().toString());
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
     * 遍历指定路径下的文件
     * @param path 遍历目标目录
     * @return 子元素流
     * @throws IOException
     */
    public Stream<Path> list(Path path) throws IOException {
        return Files.list(path);
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

    /**
     * 删除时验证是否为系统文件，如果是系统文件则不允许删除
     * @param target 删除目标
     * @param exclude 受保护系统文件列表
     * @return
     * @throws IOException
     */
    public Path deltree(Path target, List<String> exclude) throws IOException {
        List<Path> paths = new ArrayList<>();
        exclude.forEach(it -> paths.add(to(it)));
        return Files.walkFileTree(target, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                paths.forEach(it -> {
                    if(dir.startsWith(it))
                        issys = true;
                });
                if(issys) {
                    log.info("系统目录不允许删除！" + relative(dir));
                    return FileVisitResult.SKIP_SUBTREE;
                }
                Files.delete(dir);
                issys = false;
                log.info("删除操作处理目录 " + relative(dir));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                paths.forEach(it -> {
                    if(file.startsWith(it))
                        issys = true;
                });
                if(issys) {
                    log.info("系统文件不允许删除！" + relative(file));
                } else {
                    Files.delete(file);
                    log.info("删除文件 " + relative(file));
                }
                issys = false;
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public List<String> readAllLines(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    public byte[] readAllBytes(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    public String read(Path path, String separator) throws IOException {
        StringBuilder strbuil = new StringBuilder();
        readAllLines(path).forEach(it -> strbuil.append(it + separator));
        return strbuil.toString();
    }

    public Path write(Path path, Iterable<String> lines) throws IOException {
        return Files.write(path, lines, StandardCharsets.UTF_8);
    }

    public Path write(Path path, byte[] data) throws IOException {
        return Files.write(path, data);
    }

    public String getRootpath() {
        return rootpath;
    }

    public Path getRoot() {
        return root;
    }

    public Path getApproot() {
        return approot;
    }
}
