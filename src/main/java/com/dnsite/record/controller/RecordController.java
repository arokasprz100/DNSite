package com.dnsite.record.controller;

import com.dnsite.record.model.Record;
import com.dnsite.record.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/records")
public class RecordController {

    private RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping
    public String getRecords(Model model) {
        model.addAttribute("recordForm", new Record());
        model.addAttribute("recordsList", recordService.findAll());
        return "records";
    }

    @PostMapping
    public String addRecord(@ModelAttribute("recordForm") Record recordForm, BindingResult bindingResult, Model model) {

        recordService.save(recordForm);
        return "redirect:records";
    }


}
