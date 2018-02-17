package com.demo.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		String name="DevOps";
		int FINAL_COUNT=5;
		SpringApplication.run(DemoApplication.class, args);
	}
}
