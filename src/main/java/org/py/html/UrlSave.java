package org.py.html;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.py.util.EnvironmentUtil;
import org.py.util.FilesUtil;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class UrlSave {
    private EnvironmentUtil env;
    private String root;
    private String filepath;
    private FilesUtil filesUtil;
    /**
     * 允许抓去的文件扩展名
     */
    private String[] allocType = {"jpg", "jpeg", "bmp", "gif", "png", "js", "css"};

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

    /**
     * 识别文件类型；
     * 用于url中没有明确文件名时识别文件类型；
     * 文件类型必须存在于指定允许类型
     * @param filename
     * @return
     */
    public String filetype(String filename) {
        AtomicBoolean matching = new AtomicBoolean(false);
        AtomicReference<String> re = new AtomicReference<>();
        for(String it : allocType)
            if(filename.toLowerCase().contains(it)) {
                matching.set(true);
                re.set(it);
                break;
            }
        return re.get();
    }

    public String extensionFilename(String filename) {
        String extensionName = FilenameUtils.getExtension(filename);
        if(extensionName.isEmpty())
            extensionName = filetype(filename);
        return extensionName;
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

    public String getRemoteFile(String url, String savepath) {
        File save = null;
        try {
            URL httpurl = new URL(url);
            String fn = "1.jpg";
            save = new File(savepath + File.separator + fn);
            FileUtils.copyURLToFile(httpurl, save);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return save.getAbsolutePath();
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
