package com.dnsite.supermaster.controller;

import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.model.SupermasterId;
import com.dnsite.supermaster.service.SupermasterService;
import com.vladmihalcea.hibernate.type.basic.Inet;
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

    @GetMapping
    @RequestMapping("/delete/{ip}/{nameserver}")
    @ResponseBody
    public String delete(@PathVariable("ip") Inet id, @PathVariable("nameserver") String nameserver) {
        SupermasterId supermasterId = new SupermasterId(id, nameserver);
        supermasterService.delete(supermasterId);
        return "Supermaster deleted";
    }

    @PostMapping
    @RequestMapping("/deleteInBatch")
    @ResponseBody
    public String deleteInBatch(@RequestBody List<Supermaster> supermasters) {
        supermasterService.deleteInBatch(supermasters);
        return "Supermasters deleted";
    }

    @PostMapping
    @ResponseBody
    public String saveSupermasters(@RequestBody List<Supermaster> supermasters) {
        supermasterService.saveInBatch(supermasters);
        return "Supermasters saved";
    }


}
