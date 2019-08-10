package com.dnsite.record.service;


import com.dnsite.record.model.Record;
import com.dnsite.record.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public List<Record> findAll() {
        return recordRepository.findAll();
    }

    public List<Record> findByDomain_Id(Long domainId) {
        return recordRepository.findByDomain_Id(domainId);
    }

    public void saveOrUpdate(List<Record> records) {
        recordRepository.saveAll(records);
    }

    public void deleteInBatch(List<Record> records) {
        recordRepository.deleteInBatch(records);
    }
}
