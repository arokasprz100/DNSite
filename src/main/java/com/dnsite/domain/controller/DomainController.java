package com.dnsite.domain.controller;

import com.dnsite.domain.model.Domain;
import com.dnsite.domain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DomainController {

    @Autowired
    DomainService domainService;

    @ResponseBody
    public List<Domain> findAll(){return domainService.findAll();}

    @ResponseBody
    public String deleteInBatch(@RequestBody List<Domain> domains){
        domainService.deleteInBatch(domains);
        return "Domains deleted";
    }

    @ResponseBody
    public String saveDomains(@RequestBody List<Domain> domains){
        domainService.saveInBatch(domains);
        return "Domains saved";
    }

}