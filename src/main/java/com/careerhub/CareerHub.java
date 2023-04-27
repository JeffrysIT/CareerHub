package com.careerhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.careerhub.repository")
@EntityScan(basePackages = "com.careerhub.model")
public class CareerHub {
    public static void main(String[] args) {
        SpringApplication.run(CareerHub.class, args);
    }
}
