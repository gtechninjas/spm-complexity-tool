package com.spm.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.spm.back") 
public class BackApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

}
