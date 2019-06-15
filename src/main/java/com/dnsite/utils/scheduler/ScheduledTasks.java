package com.dnsite.utils.scheduler;

import com.dnsite.utils.backup.BackupPostgresql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;


@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final BackupPostgresql backupPostgresql = new BackupPostgresql();

    @Async
    @Scheduled(fixedRate = 10000)
    public void makeDailyBackup() {

        if(backupPostgresql.pathValidation()){
            try {
                log.info("Creating database backup");
                backupPostgresql.createPostgreSQLBackup();
                log.info("Database backup created");
            }

            catch (IOException | InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}
