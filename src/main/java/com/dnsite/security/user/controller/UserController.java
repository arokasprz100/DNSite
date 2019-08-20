package com.dnsite.security.user.controller;

import com.dnsite.security.service.SecurityService;
import com.dnsite.security.user.model.Role;
import com.dnsite.security.user.model.User;
import com.dnsite.security.user.service.EmailService;
import com.dnsite.security.user.service.UserService;
import com.dnsite.security.user.validator.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class UserController {
    private final static Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/remind-passwd", method = RequestMethod.GET)
    public String remindPasswd(Model model) {
        model.addAttribute("userForm", new User());

        return "remind-passwd";
    }

    @RequestMapping(value = "/remind-passwd", method = RequestMethod.POST)
    public String remindPasswd(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

        String tempPassword = userService.setUserTemporaryPassword(userForm.getUsername());
        emailService.sendTempPasswdMessage(userService.findByUsername(userForm.getUsername()).getEmail(), userForm.getUsername(), tempPassword);

        return "redirect:/dnsite";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model){

        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        List<User> admins = userService.findByRole(Role.ADMIN.getAuthority());
        if (admins.size() == 0){
            userForm.setRole(Role.ADMIN.getAuthority());
            userService.save(userForm);
            log.info("First user of database saved");
        }
        else {
            log.info("Another user want to join system");

            for(User admin : admins) {
                emailService.sendConfirmMessage(admin.getEmail(), userForm.getUsername(), userForm.getEmail());
            }
            userForm.setRole(Role.USER.getAuthority());
            userService.save(userForm);
            log.info("Email was send to verification");

            return "redirect:/login";
        }

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @RequestMapping(value = {"/403"}, method = RequestMethod.GET)
    public String accessDeny(Model model) {
        return "403";
    }

}