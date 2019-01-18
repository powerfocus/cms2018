package org.py;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan({"org.py.mapper"})
public class ApplicationCMS {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationCMS.class);
    }
}
