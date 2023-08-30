package com.johncla.cards;

import jdk.jfr.Enabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.johncla.cards.repository")
@ComponentScan(basePackages = {"com.johncla.cards.service",
        "com.johncla.cards.serviceimpl","com.johncla.cards.controller",
        "com.johncla.cards.config","com.johncla.cards.auth","com.johncla.cards.utility","com.johncla.cards.dao"})
@EntityScan(basePackages = {"com.johncla.cards.model"})
@SpringBootApplication
public class CardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardsApplication.class, args);
    }

}
