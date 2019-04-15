package com.dnsite.security.user.controller;

import com.dnsite.security.service.SecurityService;
import com.dnsite.security.user.model.User;
import com.dnsite.security.user.service.UserService;
import com.dnsite.security.user.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserValidator userValidator;

    @Mock
    private User user;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @Mock
    private SecurityService securityService;

    @Mock
    private UserService userService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void firstUserInDatabaseSaveTest(){
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Mockito.when(userService.findAll()).thenReturn(Collections.emptyList());

        try {
            assertEquals("redirect:/welcome", userController.registration(user, bindingResult, model));
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }
    }

    @Test(expected = Exception.class)
    public void notFirstUserInDatabaseNeedConfirmToSaveTest() throws Exception {
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Mockito.when(userService.findAll()).thenReturn(Collections.singletonList(new User()));
        userController.registration(user, bindingResult, model);
    }
}