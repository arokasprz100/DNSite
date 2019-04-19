package com.dnsite.record.controller;

import com.dnsite.record.model.Record;
import com.dnsite.record.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RecordController {

    private RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping(value = "/records", method = RequestMethod.GET)
    public String getRecords(Model model) {
        model.addAttribute("recordForm", new Record());
        model.addAttribute("recordsList", recordService.findAll());
        return "records";
    }

    @RequestMapping(value = "/records", method = RequestMethod.POST)
    public String addRecord(@ModelAttribute("recordForm") Record recordForm, BindingResult bindingResult, Model model) {

        recordService.save(recordForm);
        return "redirect:records";
    }


}
