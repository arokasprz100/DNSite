package com.dnsite.zone.controller;


import com.dnsite.zone.model.Zone;
import com.dnsite.zone.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ZoneController {
    private ZoneService zoneService;

    @Autowired
    ZoneController(ZoneService zoneService){
        this.zoneService = zoneService;
    }

    @RequestMapping(value = "/zones", method = RequestMethod.GET)
    public String listZones(Model model){
        model.addAttribute("zonesList", zoneService.listAll());
        model.addAttribute("iksde", "dzialaj");
        return "zones";
    }
}
