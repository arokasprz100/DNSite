package com.dnsite.utils.hibernate;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Objects;

public class DbConfigService {
    public boolean validateFirstUserExistance(String fileName){
        File f = new File(fileName);
        // config file not exists
        if(f.isFile()){
            return false;
        }
        return true;
    }

    public void createYAMLFile(DbConfig user, String fileName){
        Yaml yaml = new Yaml();
        String output = yaml.dump(user);
        byte[] sourceByte = output.getBytes();
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(sourceByte);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DbConfig readDbConfigFile(String fileName){
        Yaml yaml = new Yaml();
        try {
            waitForFileExists(fileName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        InputStream ios = null;
        try {
            ios = new FileInputStream(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DbConfig obj = yaml.load(ios);
        try {
            Objects.requireNonNull(ios).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private void waitForFileExists(String filename) throws InterruptedException {
        File f = new File(filename);
        if(!f.isFile()){
            Thread.sleep(1000);
            waitForFileExists(filename);
        }
    }
}
