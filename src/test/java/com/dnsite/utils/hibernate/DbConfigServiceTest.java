package com.dnsite.utils.hibernate;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DbConfigServiceTest {

    @After
    public void tearDown(){
        try {
            Files.deleteIfExists(Paths.get("test.yaml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File f = new File("test.yaml");
        assertFalse(f.isFile());
    }

    @Test
    public void testValidateYAMLFileCreation(){
        DbConfigService dbConfigService = new DbConfigService();
        DbConfig dbConfig = new DbConfig();
        dbConfig.setPassword("TEST");
        dbConfig.setUsername("TEST");
        dbConfigService.createYAMLFile(dbConfig,"test.yaml");
        assertEquals("TEST", dbConfigService.readDbConfigFile("test.yaml").getPassword());
        assertEquals("TEST", dbConfigService.readDbConfigFile("test.yaml").getUsername());
    }

    @Test
    public void testValidateUserExistance(){
        DbConfigService dbConfigService = new DbConfigService();
        DbConfig dbConfig = new DbConfig();
        dbConfig.setPassword("TEST");
        dbConfig.setUsername("TEST");
        dbConfigService.createYAMLFile(dbConfig,"test.yaml");
        assertFalse(dbConfigService.validateFirstUserExistance("test.yaml"));
    }
}