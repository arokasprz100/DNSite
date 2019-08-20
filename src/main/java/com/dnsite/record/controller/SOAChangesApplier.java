package com.dnsite.record.controller;

import com.dnsite.domain.model.Domain;
import com.dnsite.record.model.Record;
import com.dnsite.record.model.RecordType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SOAChangesApplier {

    public static List<Domain> apply(List<Record> records)
    {
        List<Domain> domainsWithSOARecords = new ArrayList<>();
        for(Record record : records){
            if (record.getType().equals(RecordType.SOA) && !domainsWithSOARecords.contains(record.getDomain())){
                domainsWithSOARecords.add(record.getDomain());
            }
        }
        for(Domain domain : domainsWithSOARecords){
            Integer serial = domain.getNotifiedSerial();
            String str = serial.toString();
            if(str.contains(Domain.dateFormat.format(new Date()))){
                domain.setNotifiedSerial(serial + 1);
            }
            else {
                domain.setNotifiedSerial(Integer.parseInt(Domain.dateFormat.format(new Date()))*100);
            }
        }

        return domainsWithSOARecords;
    }
}
