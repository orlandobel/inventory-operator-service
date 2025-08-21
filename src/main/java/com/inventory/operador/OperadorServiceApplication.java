package com.inventory.operador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OperadorServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OperadorServiceApplication.class, args);
    }
}
