package com.shuige.weblog.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class WebblogWebApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testLog(){
        log.info("这是一行Info级别日志");
        log.warn("这是一行warn级别日志");
        log.error("这是一行error级别日志");

        String author = "felix";
        log.info("作者:{}",author);
    }

}
