package org.py.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FilesUtil {
    private String rootpath;
    private Path root;
    private List<Path> filelist;
    private List<Path> dirlist;
    private void init() throws IOException {
        ClassPathResource resource = new ClassPathResource(rootpath);
        Path root = Paths.get(resource.getFile().getAbsolutePath());
        filelist = new ArrayList<>();
        dirlist = new ArrayList<>();
        Files.list(root)
                .forEach(it -> {
                    if(Files.isDirectory(it))
                        dirlist.add(it);
                    else
                        filelist.add(it);
                });
    }

    public FilesUtil(String rootpath) throws IOException {
        this.rootpath = rootpath;
        init();
    }

    public String getRootpath() {
        return rootpath;
    }

    public List<Path> getFilelist() {
        return filelist;
    }

    public List<Path> getDirlist() {
        return dirlist;
    }
}
