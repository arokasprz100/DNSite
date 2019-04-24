package com.dnsite.record.service;

import com.dnsite.record.model.Record;

import java.util.List;

public interface RecordService {
    void save(Record record);

    List<Record> findAll();

    void saveOrUpdate(List<Record> records);
}
