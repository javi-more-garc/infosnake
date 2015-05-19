package com.jmg.iic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * @author Javier Moreno Garcia
 *
 */

@SpringBootApplication
@EnableScheduling
@PropertySources({ @PropertySource("classpath:/conf.properties"),
		@PropertySource(value = "file:conf.properties", ignoreResourceNotFound = true) })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
}
