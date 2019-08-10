package com.dnsite.record.controller;

import com.dnsite.domain.service.DomainService;
import com.dnsite.record.DTOs.RecordDTO;
import com.dnsite.record.DTOs.RecordDTOToRecordConverter;
import com.dnsite.record.model.Record;
import com.dnsite.record.model.RecordType;
import com.dnsite.record.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private DomainService domainService;

    @GetMapping
    @RequestMapping("/all")
    @ResponseBody
    public List<RecordDTO> getRecords() {

        List<Record> records = recordService.findAll();
        List<RecordDTO> recordsToClient = new ArrayList<>();
        for(Record record : records) {
            recordsToClient.add(new RecordDTO(record));
        }
        return recordsToClient;
    }

    @GetMapping
    @RequestMapping("/{domainId}")
    @ResponseBody
    public List<RecordDTO> getRecordsFromGivenDomain(@PathVariable("domainId") Long domainId) {

        List<Record> foundRecords = recordService.findByDomain_Id(domainId);
        List<RecordDTO> recordsToClient = new ArrayList<>();
        for(Record record : foundRecords) {
            recordsToClient.add(new RecordDTO(record));
        }
        return recordsToClient;
    }

    @PostMapping
    @RequestMapping("/commit")
    @ResponseBody
    public String commitChanges(@RequestBody List<RecordDTO> recordsFromClient) {
        List<Record> records = new ArrayList<>();
        for(RecordDTO recordFromClient : recordsFromClient){
            records.add(RecordDTOToRecordConverter.convert(recordFromClient, domainService));
        }
        recordService.saveOrUpdate(records);
        domainService.saveInBatch(SOAChangesApplier.apply(records));
        return "Changes applied.";
    }

    @PostMapping
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteRecords(@RequestBody List<Record> records) {
        recordService.deleteInBatch(records);
        domainService.saveInBatch(SOAChangesApplier.apply(records));
        return "Records deleted.";
    }

    @GetMapping
    @RequestMapping("/types")
    @ResponseBody
    public RecordType[] getRecordTypes(){
        return RecordType.values();
    }
}
