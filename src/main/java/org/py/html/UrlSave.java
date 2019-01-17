package org.py.html;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.py.util.EnvironmentUtil;
import org.py.util.FilesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
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

    private String domainStr(String domain) {
        return domain.replace(Html.HTTP, "").replace(Html.HTTPS, "");
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
     *
     * @param filename
     * @return
     */
    public String filetype(String filename) {
        AtomicBoolean matching = new AtomicBoolean(false);
        AtomicReference<String> re = new AtomicReference<>();
        for (String it : allocType)
            if (filename.toLowerCase().contains(it)) {
                matching.set(true);
                re.set(it);
            }
        return re.get();
    }

    /**
     * 检查文件类型是否在允许范围内
     *
     * @param type
     * @return 是否允许文件类型
     */
    public boolean inAllocType(String type) {
        return Stream.of(allocType).anyMatch(v -> v.equals(type));
    }

    public String extensionFilename(String filename) {
        String extensionName = FilenameUtils.getExtension(filename);
        if (extensionName.isEmpty())
            extensionName = filetype(filename);
        if (!extensionName.isEmpty() && extensionName.contains("?"))
            extensionName = extensionName.substring(0, extensionName.lastIndexOf("?"));
        return extensionName;
    }

    /**
     * 获得html内容
     *
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

    public boolean isAbstractUrl(String url, String domain) {
        domain = domainStr(domain);
        if (url.startsWith(domain))
            return true;
        return false;
    }

    /**
     *
     * @param httpUrl
     *  保存目标url地址
     * @param savepath
     *  保存本地地址
     * @throws IOException
     */
    public void getRemoteFile(String httpUrl, String savepath) throws IOException {
        httpUrl = httpUrl.startsWith(Html.HTTP) || httpUrl.startsWith(Html.HTTPS) ? httpUrl : Html.HTTP.concat(httpUrl);
        HttpConnection connect = (HttpConnection) Html.connect(httpUrl);
        Document document = Html.get(connect);
        String domain = document.baseUri().endsWith(Html.SEPARATOR) ? document.baseUri() : document.baseUri().concat(Html.SEPARATOR);
        Elements srcs = document.getElementsByAttribute("src");
        ExecutorService pool = Executors.newCachedThreadPool();
        Map<String, String> re = new LinkedHashMap<>();
        srcs.stream().map(src -> src.attr("src")).collect(Collectors.toList())
                .forEach(src -> {
                    try {
                        if (src.startsWith(Html.HOSTSEPARATOR))
                            src = src.substring(2);
                        if (!src.startsWith(Html.HTTP) && !src.startsWith(Html.HTTPS))
                            src = domain.concat(src);
                        String ext = FilenameUtils.getExtension(src);
                        ext = ext.lastIndexOf("?") == -1 ? ext : ext.substring(ext.lastIndexOf("?") + 1);
                        String fn = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
                        if (!ext.isEmpty()) {
                            URL url = new URL(src);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            if (httpURLConnection.getResponseMessage().equals("OK")) {
                                File file = new File(savepath + File.separator + fn + "." + ext);
                                if(inAllocType(ext)) {
                                    pool.execute(() -> {
                                        try {
                                            FileUtils.copyURLToFile(url, file);
                                        } catch (IOException e) {
                                            LOGGER.error(e.getMessage());
                                        }
                                    });
                                    LOGGER.info("save remote files " + file.getAbsolutePath());
                                    String content = FileUtils.readFileToString(file);
                                    re.put(src, file.getName());
                                    if (content.contains("404") || content.contains("405") || content.contains("502"))
                                        FileUtils.deleteQuietly(file);
                                } else {
                                    LOGGER.info("nonsupport file type." + ext);
                                }
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                    }
                });
        pool.shutdown();
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
