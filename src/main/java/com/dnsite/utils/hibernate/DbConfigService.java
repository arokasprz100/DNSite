package com.dnsite.utils.hibernate;

import org.yaml.snakeyaml.Yaml;

import java.io.*;

public class DbConfigService {
    public boolean validateFirstUserExistance(){
        File f = new File("dbconfig.yaml");
        // config file not exists
        if(f.isFile()){
            return false;
        }
        return true;
    }

    public void createYAMLFile(DbConfig user){
        Yaml yaml = new Yaml();
        String output = yaml.dump(user);
        byte[] sourceByte = output.getBytes();
        File file = new File("dbconfig.yaml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(sourceByte);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DbConfig readDbConfigFile(){
        Yaml yaml = new Yaml();
        try {
            waitForFileExists("dbconfig.yaml");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        InputStream ios = null;
        try {
            ios = new FileInputStream(new File("dbconfig.yaml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DbConfig obj = yaml.load(ios);
        System.out.println("DUPA");
        System.out.println(obj.getUsername());
        try {
            ios.close();
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
