package com.ytzl.itrip.search.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by sam on 2018/4/20.
 */
@EnableSwagger2
//@EnableWebMvc
@ComponentScan("com.ytzl.itrip.search.controller")
@Configuration  // beans
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket createDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo()) //API信息
                .select() //扫描哪个路径下的哪些API
                .apis(RequestHandlerSelectors.any()) //任何API
                .paths(PathSelectors.any())//任何路径
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title("爱旅行项目search酒店查询模块")
                .contact(new Contact("于洪乾", "", "yhq1913@sina.com"))
                .version("V1.0").build();
    }


}
