package com.dnsite.administration.controller;

import com.dnsite.security.user.model.Role;
import com.dnsite.security.user.model.User;
import com.dnsite.security.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/administration")
public class AdministrationController {

    private static final Logger logger = LoggerFactory.getLogger(AdministrationController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user-confirm/all", method = RequestMethod.GET)
    @ResponseBody
    public List<User> userConfirmation(){
        List<User> usersToConfirm = userService.findAll()
                .stream().filter(a -> !a.getRole().equals("ADMIN"))
                .collect(Collectors.toList());
        return usersToConfirm;
    }

    @RequestMapping(value = "/user-confirm/{id}/accept", method = RequestMethod.GET)
    @ResponseBody
    public String userConfirmationAccepted(@PathVariable Long id){
        userService.updateUserRole(id, Role.ADMIN);
        logger.info("User with id: " + id + " confirmed");
        return "User accepted";
    }

    @RequestMapping(value = "/user-confirm/{id}/reject", method = RequestMethod.GET)
    @ResponseBody
    public String userConfirmationRejected(@PathVariable Long id){
        userService.deleteUserById(id);
        logger.info("User with id: " + id + " is rejected and deleted;");
        return "User rejected";
    }
}
