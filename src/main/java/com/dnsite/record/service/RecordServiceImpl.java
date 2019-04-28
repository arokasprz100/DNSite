package com.dnsite.record.service;

import com.dnsite.history.service.HistoryService;
import com.dnsite.record.model.Record;
import com.dnsite.record.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private HistoryService historyService;

    public List<Record> findAll() {
        return recordRepository.findAll();
    }

    public void saveOrUpdate(List<Record> records) {
        recordRepository.saveAll(records);
        historyService.save("RECORD", "SAVE");
    }

    public void deleteInBatch(List<Record> records) {
        recordRepository.deleteInBatch(records);
        historyService.save("RECORD", "DELETE");
    }
}
