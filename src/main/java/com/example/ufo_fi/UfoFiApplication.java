package com.example.ufo_fi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class UfoFiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UfoFiApplication.class, args);
	}

}
