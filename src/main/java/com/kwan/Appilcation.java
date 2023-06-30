package com.kwan;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)   // 开启事务管理
@MapperScan("com.kwan.dao")
public class Appilcation {
    public static void main(String[] args) {
        SpringApplication.run(Appilcation.class,args);
        log.info("项目启动成功...");
    }
}
