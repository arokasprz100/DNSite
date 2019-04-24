package com.dnsite.domain.controller;

import com.dnsite.domain.model.Domain;
import com.dnsite.domain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/domains")
public class DomainController {

    @Autowired
    DomainService domainService;

    @GetMapping
    public String getPage(Model model){return "domains";}

    @GetMapping
    @RequestMapping("/all")
    @ResponseBody
    public List<Domain> findAll(){return domainService.findAll();}

    @GetMapping
    @RequestMapping("/deleteInBatch")
    @ResponseBody
    public String deleteInBatch(@RequestBody List<Domain> domains){
        domainService.deleteInBatch(domains);
        return "Domains deleted";
    }

    @PostMapping
    @ResponseBody
    public String saveDomains(@RequestBody List<Domain> domains){
        domainService.saveInBatch(domains);
        return "Domains saved";
    }

}
