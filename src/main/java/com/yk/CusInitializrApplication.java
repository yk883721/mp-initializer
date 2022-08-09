package com.yk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yk.mapper")
public class CusInitializrApplication {

    public static void main(String[] args) {
        SpringApplication.run(CusInitializrApplication.class, args);
    }

}
