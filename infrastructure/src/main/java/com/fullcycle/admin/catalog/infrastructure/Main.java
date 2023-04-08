package com.fullcycle.admin.catalog.infrastructure;

import com.fullcycle.admin.catalog.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("hello");
        SpringApplication.run(WebServerConfig.class,args);
    }
}