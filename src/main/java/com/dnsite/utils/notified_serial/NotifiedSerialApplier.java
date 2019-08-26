package com.dnsite.utils.notified_serial;

import com.dnsite.domain.model.Domain;
import com.dnsite.record.model.Record;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotifiedSerialApplier {
    public static List<Domain> toDomain(List<Domain> domainsToApply){
        for(Domain domain : domainsToApply){

            if (domain.getId() == null) {
                domain.setNotifiedSerial(Integer.parseInt(Domain.dateFormat.format(new Date()))*100);
            }
            else {
                int serial = domain.getNotifiedSerial();
                domain.setNotifiedSerial((int)((serial + 1)%1e10));
            }
        }
        return domainsToApply;
    }

    public static List<Record> toSOARecord(List<Record> recordsSOAToApply){
        for(Record record : recordsSOAToApply){
            Pattern pattern = Pattern.compile("(\\S+ +\\S+ +)(?<notifiedSerial>\\d{10})( +\\d+ +\\d+ +\\d+ +\\d+)");
            Matcher matcher = pattern.matcher(record.getContent());
            if(matcher.matches()){
                record.setContent(matcher.replaceAll("$1" + record.getDomain().getNotifiedSerial() + "$3"));
            }
        }

        return recordsSOAToApply;
    }
}