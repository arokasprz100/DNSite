package com.dnsite;

import com.dnsite.utils.hibernate.DbConfig;
import com.dnsite.utils.hibernate.DbConfigService;
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
			DbConfigService dbConfigService = new DbConfigService();

			if(dbConfigService.validateFirstUserExistance()){
				Application.launch(StartUpTest.class);
				StartUpTest startUpTest = StartUpTest.waitForStartUpTest();
				startUpTest.printSomething();
				dbConfigService.createYAMLFile(startUpTest.getDbConfig());
				DbConfig dbConfig = startUpTest.getDbConfig();
				System.setProperty("spring.datasource.username", dbConfig.getUsername());
				System.setProperty("spring.datasource.password", dbConfig.getPassword());
			}else{
				DbConfig dbConfig = dbConfigService.readDbConfigFile();
				System.setProperty("spring.datasource.username", dbConfig.getUsername());
				System.setProperty("spring.datasource.password", dbConfig.getPassword());
			}
			System.setProperty("spring.mvc.view.prefix", "/");
			System.setProperty("spring.mvc.view.suffix", ".jsp");
			System.setProperty("server.port", "8001");
			System.setProperty("spring.h2.console.enabled", "true");
			System.setProperty("spring.h2.console.path", "/h2");
			System.setProperty("spring.datasource.url", "jdbc:h2:file:./test");
			System.setProperty("spring.datasource.url-class-name", "org.h2.Driver");
			System.setProperty("spring.jpa.hibernate.ddl-auto", "update");
			System.setProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation", "true");
			System.setProperty("spring.jpa.show-sql", "true");
			System.setProperty("spring.messages.basename", "validation");
			System.setProperty("spring.mail.host", "smtp.gmail.com");
			System.setProperty("spring.mail.port", "587");
			System.setProperty("spring.mail.username", "dnsiteofficial@gmail.com");
			System.setProperty("spring.mail.password", "dnsite123");
			System.setProperty("spring.mail.properties.mail.smtp.auth", "true");
			System.setProperty("spring.mail.properties.mail.smtp.starttls.enable", "true");

			SpringApplication.run(DNSiteApplication.class, args);
		}
	}


}
