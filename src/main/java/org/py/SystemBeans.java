package org.py;

import org.py.util.FilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@PropertySource({"classpath:public/systemProperties.properties"})
public class SystemBeans {
    @Autowired
    private Environment env;

    @Bean
    public FilesUtil filesUtil() throws IOException {
        FilesUtil filesUtil = new FilesUtil(env.getProperty("rootpath"));
        return filesUtil;
    }
}
