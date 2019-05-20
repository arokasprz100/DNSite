package com.dnsite.utils.hibernate;

public class EnvironmentConfig {
    public static void setCommonProperties(){
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
    }

    public static void setPropertiesIFDbWasConfigured(DbConfig dbConfig){
        System.setProperty("spring.datasource.username", dbConfig.getUsername());
        System.setProperty("spring.datasource.password", dbConfig.getPassword());
    }

    public static void setDbOnFirstUse(DbConfig dbConfig){
        System.setProperty("spring.datasource.username", dbConfig.getUsername());
        System.setProperty("spring.datasource.password", dbConfig.getPassword());
    }
}
