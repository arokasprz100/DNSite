package com.dnsite;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DNSiteApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
		return applicationBuilder.sources(DNSiteApplication.class);
	}

	public static void main(String[] args) {
		synchronized (DNSiteApplication.class) {
			Application.launch(StartUpTest.class);
			StartUpTest startUpTest = StartUpTest.waitForStartUpTest();
			startUpTest.printSomething();
			System.setProperty("spring.datasource.username", startUpTest.getUsername());
			//trzeba ustawic wszystkie property z palca
			//dodac wszystko co potrzebne dla bazy
			SpringApplication.run(DNSiteApplication.class, args);
		}
	}
}
