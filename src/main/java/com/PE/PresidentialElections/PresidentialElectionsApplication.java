package com.PE.PresidentialElections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PresidentialElectionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PresidentialElectionsApplication.class, args);
	}

}
