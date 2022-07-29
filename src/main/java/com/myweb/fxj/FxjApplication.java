package com.myweb.fxj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

// 先取消数据库连接驱动
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication()
// 开启Mapper扫描，指定包
@MapperScan("com.myweb.fxj.mapper")
public class FxjApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxjApplication.class, args);
    }


}


