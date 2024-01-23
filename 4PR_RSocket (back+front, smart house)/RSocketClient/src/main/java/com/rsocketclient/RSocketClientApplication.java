package com.rsocketclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RSocketClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RSocketClientApplication.class, args);
		System.out.println("Client started");
	}

}
