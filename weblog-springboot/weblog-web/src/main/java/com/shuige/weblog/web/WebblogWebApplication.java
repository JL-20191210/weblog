package com.shuige.weblog.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.shuige.weblog.*")
public class WebblogWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebblogWebApplication.class, args);
    }

}
