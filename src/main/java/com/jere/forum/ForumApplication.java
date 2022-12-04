package com.jere.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForumApplication {

	public static void main(String[] args) {
		System.out.println("Hola");
		SpringApplication.run(ForumApplication.class, args);
	}

}
