package com.example.patterns;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
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