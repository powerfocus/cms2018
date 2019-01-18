package org.py;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: pythagoras
 * @Date: 2019/1/18 22:28
 */
@Configuration
@EnableSwagger2
public class SwggerConfiguration {
    @Bean
    public Docket createRestfulApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(
                        new ApiInfoBuilder()
                        .title("spring boot API")
                        .description("JCMS2018 API test")
                        .build()
                )
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.py.controller"))
                .paths(PathSelectors.any())
                .build();
    }

}
