package com.dnsite.domain.controller;

import com.dnsite.domain.DTOs.DomainDTO;
import com.dnsite.domain.DTOs.DomainDTOToDomainConverter;
import com.dnsite.domain.model.Domain;
import com.dnsite.domain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/domains")
public class DomainController {

    @Autowired
    DomainService domainService;

    @GetMapping
    @RequestMapping("/all")
    @ResponseBody
    public List<DomainDTO> findAll(){

        List<Domain> domains = domainService.findAll();
        List<DomainDTO> domainsToClient = new ArrayList<>();
        for(Domain domain : domains) {
            domainsToClient.add(new DomainDTO(domain));
        }
        return domainsToClient;
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
    public DomainDTO findDomainById(@PathVariable("id") Long id){
        Domain domain = domainService.findById(id);
        return new DomainDTO(domain);
    }

    @GetMapping
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteDomains(@RequestBody List<Domain> domains){
        domainService.deleteInBatch(domains);
        return "Domains deleted";
    }

    @PostMapping
    @RequestMapping("/commit")
    @ResponseBody
    public String commitChanges(@RequestBody List<DomainDTO> domainsFromClient){

        List<Domain> domains = new ArrayList<>();
        for (DomainDTO domainFromClient : domainsFromClient) {
            domains.add(DomainDTOToDomainConverter.convert(domainFromClient));
        }
        domainService.saveInBatch(domains);
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