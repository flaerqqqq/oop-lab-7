package com.example.ooplab7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OopLab7Application {

    public static void main(String[] args) {
        SpringApplication.run(OopLab7Application.class, args);
    }

}
