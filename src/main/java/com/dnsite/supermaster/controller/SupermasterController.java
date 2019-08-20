package com.dnsite.supermaster.controller;

import com.dnsite.supermaster.DTOs.SupermasterDTO;
import com.dnsite.supermaster.DTOs.SupermasterDTOToSupermasterConverter;
import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.service.SupermasterService;
import com.dnsite.utils.DTOs.ConstraintViolationDTO;
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
@RequestMapping("/supermasters")
public class SupermasterController {

    @Autowired
    private SupermasterService supermasterService;

    @GetMapping
    @RequestMapping("/all")
    @ResponseBody
    public List<SupermasterDTO> findAll() {
        List<Supermaster> supermasters = supermasterService.findAll();
        List<SupermasterDTO> supermastersToClient = new ArrayList<>();
        for(Supermaster supermaster : supermasters) {
            supermastersToClient.add(new SupermasterDTO(supermaster));
        }
        return supermastersToClient;
    }

    @PostMapping
    @RequestMapping("/commit")
    @ResponseBody
    public Set<ConstraintViolationDTO> commitChanges(@RequestBody List<SupermasterDTO> supermastersFromClient) {
        List<Supermaster> supermasters = new ArrayList<>();
        Set<ConstraintViolationDTO> violations = new HashSet<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        for (SupermasterDTO supermasterFromClient : supermastersFromClient) {
            Supermaster toAdd = SupermasterDTOToSupermasterConverter.convert(supermasterFromClient);
            violations.addAll(ConstraintViolationDTO.ofSet(validator.validate(toAdd), supermasterFromClient.getTableIndex()));
            supermasters.add(toAdd);
        }

        if(violations.isEmpty()) {
            supermasterService.saveOrUpdate(supermasters);
        }
        return violations;
    }

    @PostMapping
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteSupermasters(@RequestBody List<SupermasterDTO> supermastersFromClient) {
        List<Supermaster> supermasters = new ArrayList<>();
        for (SupermasterDTO supermasterFromClient : supermastersFromClient) {
            supermasters.add(SupermasterDTOToSupermasterConverter.convert(supermasterFromClient));
        }
        supermasterService.deleteInBatch(supermasters);
        return "Supermasters deleted";
    }

}
