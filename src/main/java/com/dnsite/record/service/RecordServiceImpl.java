package com.dnsite.record.service;

import com.dnsite.record.model.Record;
import com.dnsite.record.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    private RecordRepository recordRepository;

    @Autowired
    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public void save (Record record) {
        recordRepository.save(record);
    }

    public List<Record> findAll() {
        return recordRepository.findAll();
    }
}
