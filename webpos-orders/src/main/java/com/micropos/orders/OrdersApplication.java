package com.micropos.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrdersApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name","orders");
        SpringApplication.run(OrdersApplication.class, args);
    }
}
