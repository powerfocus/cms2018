package org.py.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.file.Path;

public class UrlSave {
    private EnvironmentUtil env;
    private String root;
    private String filepath;
    private FilesUtil filesUtil;

    private Path checkSavePath() throws IOException {
        Path path = filesUtil.to(filepath);
        return filesUtil.createIfNotExists(path);
    }

    public UrlSave(EnvironmentUtil env) throws IOException {
        this.env = env;
        root = env.getEnv().getProperty("rootpath");
        filepath = env.getEnv().getProperty("filepath");
        filesUtil = new FilesUtil(root);
    }

    public String html(String url) throws IOException {
        return Jsoup.connect(url).get().html();
    }

    public Path save(String url) throws IOException {
        Path path = checkSavePath();
        Path savepath = filesUtil.to(path.toString(), filesUtil.generateDirectoryName());
        Document document = Jsoup.connect(url).get();
        String filename = filesUtil.fileName(url);
        String extname = filesUtil.extensionName(url);
        
        return savepath;
    }

    public String getRoot() {
        return root;
    }

    public String getFilepath() {
        return filepath;
    }

    public FilesUtil getFilesUtil() {
        return filesUtil;
    }
}
