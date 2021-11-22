package com.example.allganic_spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = { "com.example.controller", "com.example.service", "com.example.security",
		"com.example.jwt", "com.example.scheduler" })
@EntityScan(basePackages = { "com.example.entity" })
@EnableJpaRepositories(basePackages = { "com.example.repository" })
// interface방식은 추가
@MapperScan(basePackages = {"com.example.mappers"})
public class AllganicSpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(AllganicSpringApplication.class, args);
		System.out.println("start");
	}
}
