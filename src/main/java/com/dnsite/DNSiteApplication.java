package com.dnsite;

import com.dnsite.utils.hibernate.DbConfig;
import com.dnsite.utils.hibernate.DbConfigService;
import com.dnsite.utils.hibernate.EnvironmentConfig;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.sql.SQLException;

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
			if(dbConfigService.validateFirstUserExistance("dbconfig.yaml")){
				Application.launch(DbConnectionGUI.class);
				DbConnectionGUI dbConnectionGUI = DbConnectionGUI.waitForStartUpTest();
				validateProperEndOfInitialization(dbConnectionGUI);
				dbConfigService.createYAMLFile(dbConnectionGUI.getDbConfig(), "dbconfig.yaml");
				DbConfig dbConfig = dbConnectionGUI.getDbConfig();
				EnvironmentConfig.setDBProperties(dbConfig);
			}else{
				DbConfig dbConfig = dbConfigService.readDbConfigFile("dbconfig.yaml");
				EnvironmentConfig.setDBProperties(dbConfig);
			}
			EnvironmentConfig.setCommonProperties();

			SpringApplication.run(DNSiteApplication.class, args);
		}
	}

	private static void validateProperEndOfInitialization(DbConnectionGUI dbConnectionGUI) {
		if(dbConnectionGUI.getDbConfig().getUsername().isEmpty()){
			System.exit(1);
		}
		try {
			dbConnectionGUI.testDbConnection();
		} catch (SQLException e) {
			System.exit(1);
		}
	}


}
