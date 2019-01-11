package org.py;

import org.py.explorer.Selector;
import org.py.html.Html;
import org.py.mapper.ColumntypeMapper;
import org.py.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@PropertySource({"classpath:/public/systemProperties.properties", "classpath:/public/allowExtensionNames.properties", "classpath:/public/uploadConfiguration.properties"})
public class SystemBeans {
    @Autowired
    private Environment env;

    @Bean
    public EnvironmentUtil environmentUtil(Environment env) {
        return new EnvironmentUtil(env);
    }

    @Bean
    public FilesUtil filesUtil() throws IOException {
        FilesUtil filesUtil = new FilesUtil(env.getProperty("rootpath"));
        return filesUtil;
    }

    @Bean
    public RestfulUtil restfulUtil() {
        return new RestfulUtil();
    }

    @Bean
    public Setup setup(FilesUtil filesUtil) {
        return new Setup(filesUtil);
    }

    @Bean
    public CategoryUtil categoryUtil(ColumntypeMapper mapper) {
        CategoryUtil categoryUtil = new CategoryUtil(mapper);
        return categoryUtil;
    }

    @Bean
    public Selector selector() {
        Selector selector = new Selector();
        String allowtxt = env.getProperty("allow.text");
        String allowimg = env.getProperty("allow.img");
        selector.getAllowTxts().addAll(Arrays.asList(allowtxt.split(",")));
        selector.getAllowImgs().addAll(Arrays.asList(allowimg.split(",")));
        return selector;
    }

    @Bean
    public FileuploadUtil fileuploadUtil(FilesUtil futil) {
        FileuploadUtil fileuploadUtil = new FileuploadUtil(env.getProperty("uploaddir"), futil);
        return fileuploadUtil;
    }

    @Bean
    public UrlSave urlSave(EnvironmentUtil env) throws IOException {
        return new UrlSave(env);
    }
}
