package com.dnsite.supermaster.controller;

import com.dnsite.supermaster.DTOs.SupermasterDTO;
import com.dnsite.supermaster.DTOs.SupermasterDTOToSupermasterConverter;
import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.service.SupermasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public String commitChanges(@RequestBody List<SupermasterDTO> supermastersFromClient) {
        List<Supermaster> supermasters = new ArrayList<>();
        for (SupermasterDTO supermasterFromClient : supermastersFromClient) {
            supermasters.add(SupermasterDTOToSupermasterConverter.convert(supermasterFromClient));
        }
        supermasterService.saveOrUpdate(supermasters);
        return "Changes applied.";
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
