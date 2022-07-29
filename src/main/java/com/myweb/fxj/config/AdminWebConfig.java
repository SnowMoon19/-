//package com.myweb.fxj.config;
//
//import com.myweb.fxj.interceptor.LoginInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.*;
//
///**
// * 总的配置类
// * WebMvcConfigurer对应原生的web.xml
// */
//@Configuration
//@EnableWebMvc
//public class AdminWebConfig implements WebMvcConfigurer {
////
////    /**
////     * 添加拦截器
////     * @param registry
////     */
////    @Override
////    public void addInterceptors(InterceptorRegistry registry) {
////        // 将Login拦截器添加，设置拦截路径为全部，排除首页登录与静态资源
////        registry.addInterceptor(new LoginInterceptor())
////                .addPathPatterns("/**")
////                .excludePathPatterns("/register", "/login", "/css/**", "/js/**", "/pics/**", "/favicon.ico", "/error");// 图标这里是个坑 todo /error请求的问题
////    }
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////
////        //每次调用registry.addMappin可以添加一个跨域配置，需要多个配置可以多次调用registry.addMapping
////        registry.addMapping("/**")
////                .allowedOrigins("*") //放行哪些原始域
////                .allowedMethods("PUT", "DELETE", "POST", "GET") //放行哪些请求方式
////                .allowedHeaders("header1", "header2", "header3") //放行哪些原始请求头部信息
////                .exposedHeaders("header1", "header2") //暴露哪些头部信息
////                .allowCredentials(false) //是否发送 Cookie
////                .maxAge(3600);
////
////        // Add more mappings...
////    }
//}
