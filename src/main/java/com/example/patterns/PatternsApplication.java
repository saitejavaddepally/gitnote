package com.example.patterns;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@EnableAsync
@EnableTransactionManagement
public class PatternsApplication {

	public static void main(String[] args) {
		log.info("Starting PatternsApplication");
		try {
			SpringApplication.run(PatternsApplication.class, args);
			log.info("PatternsApplication started successfully");
		} catch (Exception e) {
			log.error("Application failed to start", e);
		}
	}
}
