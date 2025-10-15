package com.example.quotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class QuotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuotesApplication.class, args);
	}

}
