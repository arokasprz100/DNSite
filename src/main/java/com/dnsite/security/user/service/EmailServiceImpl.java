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
//            String body = "<html><head><style> " +
//                    "</style></head>" +
//                    "<body>"+
//                    "<p><h3>Hello DNSite Admin! </h3><br>User <b>" + newUser + "</b> witch e-mail adress: "+ newUserEmail +" want to be added to DNSite.<br>" +
//                    "If <b>" + newUser + "</b> is authorized to manage the database, please confirm the registration by clicking the link. <br><p>" +
//                    "<a href=\"http://localhost:8001/dnsite/administration/user-confirm\" target=\"_blank\">Confirm user registration</a> <br><br> Best regards, <br> <b>DNSIte Team</b>"
//                    +"</body></html>";

            String body = "<!DOCTYPE html> <html lang=\"en\"> <head> <meta charset=\"utf-8\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" /> <meta name=\"theme-color\" content=\"#000000\" /> <link href=\"https://fonts.googleapis.com/css?family=Montserrat:400,600,700,900|Varela\" rel=\"stylesheet\" /> <title>DNSite</title> <style> * { margin: 0; padding: 0; box-sizing: border-box; } body { background-color: rgb(240, 240, 240);" +
                    " display: flex; justify-content: center; } .mail { display: flex; flex-direction: column; text-align: center; background-color: white; margin-top: 30px; } .mail header { display: flex; flex-direction: row; background-image: url(\"./background.png\"); font-family: \"Montserrat\", sans-serif; font-size: 7vh; padding: 5px 10px; } .mail header span { color: rgb(255, 88, 0); font-weight: 600; } .mail header p { color: white; font-weight: 400; } article { display: " +
                    "flex; flex-direction: column; align-items: flex-start; padding: 20px 20px; color: #4b4b4b; } article h1 { color: #151515; margin-top: 5px; margin-bottom: 20px; font-family: \"Montserrat\", sans-serif; font-size: 4vh; }" +
                    " article p { font-family: \"Montserrat\", sans-serif; font-size: 2.5vh; }" +
                    " article p:nth-child(2) { line-height: 30px; }" +
                    " article p:nth-child(6) { line-height: 30px; }" +
                    " article a { margin-top: 30px; margin-bottom: 30px; background-color: white; color: #4b4b4b; border: 2px solid #4b4b4b; padding: 10px 20px; font-family: \"Montserrat\", sans-serif; text-transform: uppercase; text-decoration: none; font-size: 2vh; font-weight: 600; transition: 0.3s; }" +
                    " article a:hover { cursor: pointer; border: 2px solid rgb(255, 88, 0); color: rgb(255, 88, 0); }\n" +
                    " footer { background-image: url(\"./background.png\"); font-family: \"Montserrat\", sans-serif; font-size: 2.5vh; color: white; padding-top: 7px; padding-bottom: 7px; } </style> </head> <body> <div class=\"mail\"> <header> <span>DNS</span> <p>ite</p> </header> <article> <h1>Hi DNSite Admin!</h1> <p>User <strong>Example</strong> want to be added to DNSite.</p> <p> If <strong>Example</strong> is authorized to manage the database, please confirm the registration. "+
                    "</p> <a href=\"/\">Confirm User Registration</a> <p>Bext regards,</p> <p><strong>DNSiteTeam</strong></p> </article> <footer> <p>DNSite official</p> </footer> </div> </body> </html> ";

            helper.setText(body, true);

        }
        catch(MessagingException e){
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
        }

        emailSender.send(message);
    }
}
