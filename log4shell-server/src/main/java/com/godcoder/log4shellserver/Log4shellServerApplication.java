package com.godcoder.log4shellserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Log4shellServerApplication {

	public static void main(String[] args) {
		System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");
		SpringApplication.run(Log4shellServerApplication.class, args);
	}

}
