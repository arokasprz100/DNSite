package com.dnsite.security.user.service;

public interface EmailService {

    void sendConfirmMessage(String to, String newUser, String newUserEmail);
}
