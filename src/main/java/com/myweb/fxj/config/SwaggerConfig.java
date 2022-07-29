package com.myweb.fxj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口，这里扫描所有controller就行
                .apis(RequestHandlerSelectors.basePackage("com.myweb.fxj.controller"))
                .build();
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("SnowMoon","https://blog.csdn.net/weixin_48678547","2456532897@qq.com");
        return new ApiInfo(
                "项目的API", // 标题
                "", // 描述
                "", // 版本
                "", // 组织链接
                contact, // 联系人信息
                "", // 许可
                "", // 许可连接
                new ArrayList<>()// 扩展
        );
    }
}


