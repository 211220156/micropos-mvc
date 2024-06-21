package com.micropos.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.micropos.user.mapper")
//@MapperScan({"com.gitee.sunchenbin.mybatis.actable.dao.*"})//固定的
//@ComponentScan("com.gitee.sunchenbin.mybatis.actable.manager.*")//固定的
public class UserApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name","user");
        SpringApplication.run(UserApplication.class, args);
    }
}
