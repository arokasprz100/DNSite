package com.dnsite.utils.DTOs;

import com.dnsite.domain.model.Domain;
import com.dnsite.record.model.Record;
import com.dnsite.record.model.RecordType;
import com.dnsite.utils.hibernate.DbConfig;
import com.dnsite.utils.hibernate.DbConfigService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SOARecordsCreator {

    public static Record create(Domain masterDomain) {
        Record record = new Record();

        record.setDomain(masterDomain);
        record.setName(masterDomain.getName());
        record.setType(RecordType.SOA);
        record.setContent(createRecordContent());
        record.setTtl(86400L);
        record.setPriority(0L);
        record.setDisabled(false);
        record.setAuth(true);

        return record;
    }

    private static String createRecordContent() {
        DbConfigService dbConfigService = new DbConfigService();
        DbConfig dbConfig = dbConfigService.readDbConfigFile("dbconfig.yaml");
        return dbConfig.getPrimaryNameserver() + " " + dbConfig.getHostmaster() + " " + getCurrentDate()
                + "00" + " 28800 7200 604800 86400";
    }

    private static String getCurrentDate() {
        Date date = new Date();
        String strDateFormat = "yyyyMMdd";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        return dateFormat.format(date);
    }
}
