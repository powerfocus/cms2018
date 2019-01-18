package org.py;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan({"org.py.mapper"})
@EnableSwagger2
public class ApplicationCMS {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationCMS.class);
    }
}
