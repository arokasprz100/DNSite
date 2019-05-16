package com.dnsite.security.user.service;

import com.dnsite.security.user.model.User;
import com.dnsite.security.user.service.UserService;
import com.dnsite.security.user.validator.UserValidator;
import com.dnsite.user.UserValidatorTest;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;


import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    private final static Logger log = Logger.getLogger(UserValidatorTest.class);

    @InjectMocks
    private UserValidator userValidator;

    @Mock
    private UserService userServiceMock;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateTemporaryPassword() {


        when(userServiceMock.findByUsername("test123")).thenReturn(new User());
        User newUser = new User();
        newUser.setId(1L);
        newUser.setUsername("test123");
        newUser.setPassword("test123456");
        newUser.setPasswordConfirm("test123456");
        newUser.setFirstName("Jakub");
        newUser.setLastName("Nowak");
        newUser.setEmail("test123@test.com");

        UserService us = new UserServiceImpl();
//        String tmpPasswd = us.generateTemporaryPassword("test123");
//        assertEquals(true, newUser.getPassword().equals(tmpPasswd));
    }
}