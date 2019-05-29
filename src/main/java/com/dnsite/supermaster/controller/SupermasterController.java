package com.dnsite.supermaster.controller;

import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.service.SupermasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/supermasters")
public class SupermasterController {

    @Autowired
    private SupermasterService supermasterService;

    @GetMapping
    @RequestMapping("/all")
    @ResponseBody
    public List<Supermaster> findAll() {
        return supermasterService.findAll();
    }


    @PostMapping
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteSupermasters(@RequestBody List<Supermaster> supermasters) {
        supermasterService.deleteInBatch(supermasters);
        return "Supermasters deleted";
    }

    @PostMapping
    @RequestMapping("/commit")
    @ResponseBody
    public String commitChanges(@RequestBody List<Supermaster> supermasters) {
        supermasterService.saveOrUpdate(supermasters);
        return "Changes applied.";
    }

}
