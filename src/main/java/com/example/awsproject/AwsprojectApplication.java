package com.example.awsproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AwsprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsprojectApplication.class, args);
	}

	@GetMapping("/")
	public String helloWorld() {
		return "Test";
	}

}
