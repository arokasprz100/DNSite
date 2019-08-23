package com.dnsite.record.controller;

import com.dnsite.domain.model.Domain;
import com.dnsite.record.model.Record;
import com.dnsite.record.model.RecordType;

import java.util.ArrayList;
import java.util.List;

public class DomainsToChangeNotifiedSerialFinder {

    public static List<Domain> byRecords(List<Record> records)
    {
        List<Domain> domainsToChange = new ArrayList<>();
        for(Record record : records){
            if (!record.getType().equals(RecordType.SOA) && !domainsToChange.contains(record.getDomain())){
                domainsToChange.add(record.getDomain());
            }
        }
        return domainsToChange;
    }
}
