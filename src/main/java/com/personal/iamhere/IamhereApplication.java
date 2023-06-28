package com.personal.iamhere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IamhereApplication {

    public static void main(String[] args) {
        SpringApplication.run(IamhereApplication.class, args);
    }

}