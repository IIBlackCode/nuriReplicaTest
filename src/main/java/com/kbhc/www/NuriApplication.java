package com.kbhc.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NuriApplication {

	public static void main(String[] args) {
		SpringApplication.run(NuriApplication.class, args);
	}

}
