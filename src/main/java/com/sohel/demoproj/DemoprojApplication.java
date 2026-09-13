package com.sohel.demoproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.sohel.demoproj.repository")
@EnableJpaRepositories(basePackages = "com.sohel.demoproj.jpa")
@EnableRedisRepositories(basePackages = "com.sohel.demoproj.redis")
public class DemoprojApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoprojApplication.class, args);
	}

}



