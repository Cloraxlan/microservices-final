package edu.msoe.microservicefinal.snowmanPostMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class SnowmanPostMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnowmanPostMicroserviceApplication.class, args);
	}

}
