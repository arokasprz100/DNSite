package com.dnsite.utils.backup;

import com.dnsite.utils.hibernate.DbConfig;
import com.dnsite.utils.hibernate.DbConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class BackupPostgresql {

    private static final Logger log = LoggerFactory.getLogger(BackupPostgresql.class);

    private DbConfig dbConfig ;

    public BackupPostgresql(){
        DbConfigService dbConfigService = new DbConfigService();
        dbConfig = dbConfigService.readDbConfigFile("dbconfig.yaml");
    }

    public boolean pathValidation(){
        if( dbConfig.getPg_dumpLocalization().equals("null") && dbConfig.getBackupLocalization().equals("null")){
            log.warn("Backup disabled");
            return false;
        }

        File backupDest = new File(dbConfig.getBackupLocalization());
        File pg_dumpLoc = new File(dbConfig.getPg_dumpLocalization());

        if(!pg_dumpLoc.exists() ) {
            log.warn("pg_dump localization doesn't exist");
            return backupDest.exists();
        }
        if(!pg_dumpLoc.toString().contains("pg_dump")) {
            log.warn("Wrong backup program selected, pg_dump.exe required");
            return false;
        }
        if(!backupDest.exists()) {
            log.warn("Backup destination doesn't exist");
            return backupDest.exists();
        }
        return true;
    }

    public void createPostgreSQLBackup() throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        Process p;
        ProcessBuilder pb;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy_HHmmss");

        rt = Runtime.getRuntime();
        pb = new ProcessBuilder(
                dbConfig.getPg_dumpLocalization(),
                "--host", dbConfig.getHostname(),
                "--port", dbConfig.getDbPort(),
                "--username", dbConfig.getUsername(),
                "--no-password",
//                "--format", "custom", // change format, default simple sql
//                "--blobs",
//                "--verbose", // printing steps
                "--file",
                dbConfig.getBackupLocalization() + "\\backup"+ formatter.format(date) + ".sql", "test");
        try {
            final Map<String, String> env = pb.environment();
            env.put("PGPASSWORD", dbConfig.getPassword());
            p = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();
            }
            r.close();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
