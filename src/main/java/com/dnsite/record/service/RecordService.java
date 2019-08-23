package com.dnsite.record.service;

import com.dnsite.record.model.Record;
import com.dnsite.record.model.RecordType;

import java.util.List;

public interface RecordService {

    List<Record> findAll();

    List<Record> findByDomain_Id(Long domainId);

    List<Record> findByTypeAndDomain_Id(RecordType type, Long domainId);

    void saveOrUpdate(List<Record> records);

    void deleteInBatch(List<Record> records);
}
