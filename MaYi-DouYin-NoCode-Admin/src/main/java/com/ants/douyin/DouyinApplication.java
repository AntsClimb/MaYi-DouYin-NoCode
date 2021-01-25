package com.ants.douyin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DouyinApplication {

    public static void main(String[] args) {
        SpringApplication.run(DouyinApplication.class, args);
    }
}
