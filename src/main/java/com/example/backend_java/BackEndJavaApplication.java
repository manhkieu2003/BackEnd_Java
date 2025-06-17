package com.example.backend_java;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackEndJavaApplication {
//    @Value("${jwt.secretKey}")
//  private String jwtkey;
    public static void main(String[] args) {
        SpringApplication.run(BackEndJavaApplication.class, args);
    }
//    @PostConstruct
//    public void init() {
//        System.out.println("jwt secret key: " + jwtkey);
//    }

}
