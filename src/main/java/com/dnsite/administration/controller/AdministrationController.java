package com.dnsite.administration.controller;

import com.dnsite.security.user.model.Role;
import com.dnsite.security.user.model.User;
import com.dnsite.security.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/dnsite/administration")
public class AdministrationController {

    private static final Logger logger = LoggerFactory.getLogger(AdministrationController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user-confirm", method = RequestMethod.GET)
    public String userConfirmation(Model model){
        List<User> usersToConfirm = userService.findAll().stream().filter(a -> !a.getRole().equals("ADMIN")).collect(Collectors.toList());
        model.addAttribute("userForConfirmation", usersToConfirm);
        return "user-confirm";
    }

    @RequestMapping(value = "/user-confirm/{id}/accept", method = RequestMethod.GET)
    public String userConfirmationAccepted(@PathVariable Long id){
        userService.updateUserRole(id, Role.ADMIN);
        logger.info("User with id: " + id + " confirmed");
        return "redirect:/dnsite/administration/user-confirm";
    }

//    @RequestMapping(value = "/user-confirm", method = RequestMethod.POST)
//    public String userConfirmationRejected(){
//        System.out.println("USUNIETY");
//        return "user-confirm";
//    }
}
