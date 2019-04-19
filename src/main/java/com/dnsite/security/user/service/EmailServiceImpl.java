package com.dnsite.security.user.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
//import org.thymeleaf.TemplateEngine;

@Service
public class EmailServiceImpl implements EmailService {

    private final static Logger log = Logger.getLogger(EmailServiceImpl.class);

    @Autowired
    public JavaMailSender emailSender;

    public void sendConfirmMessage(String to, String newUser, String newUserEmail){
        MimeMessage message = emailSender.createMimeMessage();
        String title = "[DNSite] New user registration";
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);

            helper.setSubject(title);
            // TODO Thymeleaf template
//            String body =  new TemplateEngine().process("template", context);
            String body = "<html><head><style> " +
                    "</style></head>" +
                    "<body>"+
                    "<p><h3>Hello DNSite Admin! </h3><br>User <b>" + newUser + "</b> witch e-mail adress: "+ newUserEmail +" want to be added to DNSite.<br>" +
                    "If <b>" + newUser + "</b> is authorized to manage the database, please confirm the registration by clicking the link. <br><p>" +
                    "<a href=\"https://www.google.com\" target=\"_blank\">Confirm user registration</a> <br><br> Best regards, <br> <b>DNSIte Team</b>"
                    +"</body></html>";

            helper.setText(body, true);

        }
        catch(MessagingException e){
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
        }

        emailSender.send(message);
    }
}
