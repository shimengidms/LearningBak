package com.sangeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: 甘廿甘廿
 * @create: 2023-10-20 19:23
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.sangeng.mapper")
@EnableScheduling
@EnableSwagger2
public class SanGengBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SanGengBlogApplication.class, args);
    }
}
