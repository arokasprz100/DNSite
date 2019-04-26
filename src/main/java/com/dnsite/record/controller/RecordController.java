package com.dnsite.record.controller;

import com.dnsite.record.model.Record;
import com.dnsite.record.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

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
        recordService.saveOrUpdate(records);
        return "Changes applied.";
    }

    @PostMapping
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteRecords(@RequestBody List<Record> records) {
        recordService.deleteInBatch(records);
        return "Records deleted.";
    }

}
