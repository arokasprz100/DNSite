package com.dnsite.record.controller;

import com.dnsite.domain.model.Domain;
import com.dnsite.record.model.Record;
import com.dnsite.record.model.RecordType;
import com.dnsite.record.service.RecordService;
import com.dnsite.record.service.RecordServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class SOARecordsFinder {
    public static List<Record> byDomains(List<Domain> toSearch){

        RecordService recordService = new RecordServiceImpl();

        List<Record> recordsSOA = new ArrayList<>();

        for(Domain domain : toSearch){
            recordsSOA.addAll(recordService.findByTypeAndDomain_Id(RecordType.SOA, domain.getId()));
        }

        return recordsSOA;
    }
}
