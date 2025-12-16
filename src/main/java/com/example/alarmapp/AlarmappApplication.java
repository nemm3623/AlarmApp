package com.example.alarmapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AlarmappApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlarmappApplication.class, args);
	}

}
