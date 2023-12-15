package com.wolroys.springstockapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringStockAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStockAppApplication.class, args);
    }

}
