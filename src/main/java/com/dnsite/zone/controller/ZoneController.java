package com.dnsite.zone.controller;


import com.dnsite.zone.model.Zone;
import com.dnsite.zone.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/zones")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @GetMapping
    public String getPage(Model model){
        return "zones";
    }

    @GetMapping
    @RequestMapping("/all")
    @ResponseBody
    public List<Zone> findAll(){
        return zoneService.findAll();
    }

    @GetMapping
    @RequestMapping("/deleteInBatch")
    @ResponseBody
    public String deleteInBatch(@RequestBody List<Zone> zones){
        zoneService.deleteInBatch(zones);
        return "Zones deleted";
    }

    @PostMapping
    @ResponseBody
    public String saveDomains(@RequestBody List<Zone> zones){
        zoneService.saveInBatch(zones);
        return "Zones saved";
    }
}
