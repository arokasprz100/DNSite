package com.dnsite.record.controller;

import com.dnsite.domain.model.Domain;
import com.dnsite.domain.service.DomainService;
import com.dnsite.record.model.Record;
import com.dnsite.record.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.dnsite.domain.service.DomainServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private DomainService domainService;

    @GetMapping
    public String getPage(Model model) {
        return "records";
    }

    @GetMapping
    @RequestMapping("/all")
    @ResponseBody
    public List<Record> getRecords() {
        return recordService.findAll();
    }

    @PostMapping
    @RequestMapping("/commit")
    @ResponseBody
    public String commitChanges(@RequestBody List<Record> records) {
        for(Record rec : records){
            rec.setDomain(domainService.findById(rec.getDomain().getId()));
        }
        recordService.saveOrUpdate(records);
        SOAChanges(records);
        return "Changes applied.";
    }

    @PostMapping
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteRecords(@RequestBody List<Record> records) {
        recordService.deleteInBatch(records);
        SOAChanges(records);
        return "Records deleted.";
    }

    @GetMapping
    @RequestMapping("/types")
    @ResponseBody
    public String domainTypes(){
        String toRet = "[";
        Record.RTYPE arr[] = Record.RTYPE.values();
        for(Record.RTYPE val : arr){
            toRet += "\"" + val.toString() + "\",";
        }
        toRet = toRet.substring(0, toRet.length() - 1);
        toRet += "]";
        return toRet;
    }

    private void SOAChanges(List<Record> records){
        List<Domain> list = new ArrayList<>();
        for(Record item : records){
            if (item.getType().equals(Record.RTYPE.SOA)){
                if(!list.contains(item.getDomain())) {
                    list.add(item.getDomain());
                }
            }
        }
        for(Domain item : list){
            Integer serial = item.getNotifiedSerial();
            String str = serial.toString();
            if(str.contains(Domain.dateFormat.format(new Date()))){
                item.setNotifiedSerial(item.getNotifiedSerial()+1);
            }
            else {
                item.setNotifiedSerial(Integer.parseInt(Domain.dateFormat.format(new Date()))*100);
            }
        }
        domainService.saveInBatch(list);
    }
}
