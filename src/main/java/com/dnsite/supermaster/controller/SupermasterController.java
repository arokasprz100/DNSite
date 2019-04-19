package com.dnsite.supermaster.controller;

import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.model.SupermasterId;
import com.dnsite.supermaster.service.SupermasterService;
import com.dnsite.supermaster.validator.SupermasterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SupermasterController {

    private SupermasterService supermasterService;

    private SupermasterValidator supermasterValidator;

    @Autowired
    SupermasterController(SupermasterService supermasterService, SupermasterValidator supermasterValidator) {
        this.supermasterService = supermasterService;
        this.supermasterValidator = supermasterValidator;
    }

    @RequestMapping(value = "/supermasters", method = RequestMethod.GET)
    public String getSupermasters(Model model) {
        model.addAttribute("supermasterForm", new Supermaster());
        model.addAttribute("supermastersList", supermasterService.findAll());
        return "supermasters";
    }


    @RequestMapping(value = "/supermasters", method = RequestMethod.POST)
    public String addSupermaster(@ModelAttribute("supermasterForm") Supermaster supermasterForm, BindingResult bindingResult, Model model) {

        supermasterValidator.validate(supermasterForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "supermasters";
        }

        supermasterService.save(supermasterForm);
        return "redirect:supermasters";
    }

    @RequestMapping(value = "/supermasters/delete/{ip}/{nameserver}", method = RequestMethod.GET)
    public String deleteSupermaster(@PathVariable("ip") String ip, @PathVariable("nameserver") String nameserver) {
        SupermasterId supermasterId = new SupermasterId(ip, nameserver);
        supermasterService.delete(supermasterId);
        return "redirect:/supermasters";
    }
}
