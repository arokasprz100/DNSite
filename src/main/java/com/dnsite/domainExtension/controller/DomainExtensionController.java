package com.dnsite.domainExtension.controller;


import com.dnsite.domain.model.Domain;
import com.dnsite.domain.service.DomainService;
import com.dnsite.domainExtension.model.DomainExtension;
import com.dnsite.domainExtension.service.DomainExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/domains")
public class DomainExtensionController {

    @Autowired
    private DomainExtensionService domainExtensionService;

    @Autowired
    private DomainService domainService;
/*
    @GetMapping
    public String getPage(Model model){
        return "domains";
    }
*/
    @GetMapping
    @RequestMapping("/all")
    @ResponseBody
    public List<DomainExtension> findAll(){
        return domainExtensionService.findAll();
    }

    @GetMapping
    @RequestMapping("/types")
    @ResponseBody
    public Domain.TYPE[] domainTypes(){
        return Domain.TYPE.values();
    }

    @GetMapping
    @RequestMapping("/{id}")
    @ResponseBody
    public DomainExtension findOneById(@PathVariable("id") Long id){
        return domainExtensionService.findById(id);
    }

    @GetMapping
    @RequestMapping("/deleteInBatch")
    @ResponseBody
    public String deleteInBatch(@RequestBody List<DomainExtension> domainExtensions){
        domainExtensionService.deleteInBatch(domainExtensions);
        return "Domains deleted";
    }

    @PostMapping
    @ResponseBody
    public String saveDomains(@RequestBody List<DomainExtension> domainExtensions){
        domainExtensionService.saveInBatch(domainExtensions);
        return "Domains saved";
    }

    @GetMapping
    @RequestMapping("/domainIds")
    @ResponseBody
    public List<Long> getDomainIds() {
        List<Domain> domains = domainService.findAll();
        List<Long> domainIds = new ArrayList<>();
        for(Domain domain : domains) {
            domainIds.add(domain.getId());
        }
        return domainIds;
    }
}
