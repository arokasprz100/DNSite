package com.dnsite.domain.controller;

import com.dnsite.domain.DTOs.DomainDTO;
import com.dnsite.domain.DTOs.DomainDTOToDomainConverter;
import com.dnsite.domain.model.Domain;
import com.dnsite.domain.service.DomainService;
import com.dnsite.record.model.Record;
import com.dnsite.record.service.RecordService;
import com.dnsite.utils.DTOs.ConstraintViolationDTO;
import com.dnsite.utils.DTOs.SOARecordsCreator;
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
@RequestMapping("/domains")
public class DomainController {

    @Autowired
    private DomainService domainService;

    @Autowired
    private RecordService recordService;

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

    @PostMapping
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteDomains(@RequestBody List<Domain> domains){
        domainService.deleteInBatch(domains);
        return "Domains deleted";
    }

    @PostMapping
    @RequestMapping("/commit")
    @ResponseBody
    public Set<ConstraintViolationDTO> commitChanges(@RequestBody List<DomainDTO> domainsFromClient){

        List<Domain> domains = new ArrayList<>();
        Set<ConstraintViolationDTO> violations = new HashSet<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        List<Record> SOARecordsToAdd = new ArrayList<>();
        List<Domain> newMasterDomains = new ArrayList<>();

        for (DomainDTO domainFromClient : domainsFromClient) {
            Domain toAdd = DomainDTOToDomainConverter.convert(domainFromClient);
            if (domainFromClient.getId() == null && domainFromClient.getType() != Domain.TYPE.SLAVE) {
                newMasterDomains.add(toAdd);
            }
            violations.addAll(ConstraintViolationDTO.ofSet(validator.validate(toAdd), domainFromClient.getTableIndex()));
            domains.add(toAdd);
        }

        NotifiedSerialApplier.toDomain(newMasterDomains);
        for (Domain domain : newMasterDomains) {
            SOARecordsToAdd.add(SOARecordsCreator.create(domain));
        }

        if(violations.isEmpty()){
            domainService.saveInBatch(domains);
            recordService.saveOrUpdate(SOARecordsToAdd);
        }

        return violations;
    }

    @GetMapping
    @RequestMapping("/domainInfos")
    @ResponseBody
    public List<String> getDomainIds() {
        List<Domain> domains = domainService.findAll();
        List<String> domainInfos = new ArrayList<>();
        for(Domain domain : domains) {
            domainInfos.add(domain.getId() + " - " + domain.getName());
        }
        return domainInfos;
    }

}