package org.py.html;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.py.util.EnvironmentUtil;
import org.py.util.FilesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class UrlSave {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlSave.class);
    private EnvironmentUtil env;
    private String root;
    private String filepath;
    private FilesUtil filesUtil;
    private final String REGEXFILENAME = "([^<>\\\"\\\']+)\\.\\w{3}$";

    /**
     * 允许抓去的文件扩展名
     */
    private String[] allocType = {"jpg", "jpeg", "bmp", "gif", "png", "ico", "js", "css", "svg", "txt", "xml", "yml", "pdf", "doc", "docx", "xls", "ppt", "pptx"};

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
            }
        return re.get();
    }

    /**
     * 检查文件类型是否在允许范围内
     * @param type
     * @return 是否允许文件类型
     */
    public boolean inAllocType(String type) {
        return Stream.of(allocType).anyMatch(v -> v.equals(type));
    }

    public String extensionFilename(String filename) {
        String extensionName = FilenameUtils.getExtension(filename);
        if(extensionName.isEmpty())
            extensionName = filetype(filename);
        if(!extensionName.isEmpty() && extensionName.contains("?"))
            extensionName = extensionName.substring(0, extensionName.lastIndexOf("?"));
        return extensionName;
    }

    /**
     * 获得html内容
     * @param url
     * @return
     * @throws IOException
     */
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

    public String getRemoteFile(String domain, String url, String savepath) {
        File save = null;
        try {
            domain = domain.endsWith("/") ? domain : domain + "/";
            URL httpurl = new URL(domain + url);
            String fn;
            String extensionName = extensionFilename(url);
            if(extensionName.isEmpty())
                throw new IllegalArgumentException("无法获得文件类型，操作失败！");
            if(inAllocType(extensionName)) {
                fn = filesUtil.randomName() + "." + extensionName;
                save = new File(savepath + File.separator + fn);
                FileUtils.copyURLToFile(httpurl, save);
            } else {
                LOGGER.info("不支持的类型，保存操作未完成！" + extensionName);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null != save ? save.getAbsolutePath() : "";
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
