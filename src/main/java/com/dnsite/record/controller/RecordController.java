package com.dnsite.record.controller;

import com.dnsite.domain.model.Domain;
import com.dnsite.domain.service.DomainService;
import com.dnsite.record.DTOs.RecordDTO;
import com.dnsite.record.DTOs.RecordDTOToRecordConverter;
import com.dnsite.record.model.Record;
import com.dnsite.record.model.RecordType;
import com.dnsite.record.service.RecordService;
import com.dnsite.utils.DTOs.ConstraintViolationDTO;
import com.dnsite.utils.notified_serial.NotifiedSerialApplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Set<ConstraintViolationDTO> commitChanges(@RequestBody List<RecordDTO> recordsFromClient) {
        List<Record> records = new ArrayList<>();
        Set<ConstraintViolationDTO> violations = new HashSet<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        for(RecordDTO recordFromClient : recordsFromClient){
            Record toAdd = RecordDTOToRecordConverter.convert(recordFromClient, domainService);
            violations.addAll(ConstraintViolationDTO.ofSet(validator.validate(toAdd), recordFromClient.getTableIndex()));
            records.add(toAdd);
        }

        if(violations.isEmpty() && records.size() != 0) {
            recordService.saveOrUpdate(records);
            List<Domain> domainsToChangeNotifiedSerial = DomainsToChangeNotifiedSerialFinder.byRecords(records);
            domainService.saveInBatch(NotifiedSerialApplier.toDomain(domainsToChangeNotifiedSerial));
            List<Record> recordsSOAToChangeNotifiedSerial = SOARecordsFinder.byDomains(domainsToChangeNotifiedSerial);
            recordService.saveOrUpdate(NotifiedSerialApplier.toSOARecord(recordsSOAToChangeNotifiedSerial));
        }
        return violations;
    }

    @PostMapping
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteRecords(@RequestBody List<Record> records) {
        if (records.size() != 0) {
            recordService.deleteInBatch(records);
            domainService.saveInBatch(DomainsToChangeNotifiedSerialFinder.byRecords(records));
            return "Records deleted.";
        }
        else {
            return "No records to delete.";
        }
    }

    @GetMapping
    @RequestMapping("/types")
    @ResponseBody
    public RecordType[] getRecordTypes(){
        return RecordType.values();
    }
}
