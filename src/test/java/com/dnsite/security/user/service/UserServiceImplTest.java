package com.dnsite.security.user.service;

import com.dnsite.security.user.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Test
    public void generateTemporaryPassword() {

        User newUser = new User();
        newUser.setId(1L);
        newUser.setUsername("test123");
        newUser.setPassword("test123456");
        newUser.setPasswordConfirm("test123456");
        newUser.setFirstName("Jakub");
        newUser.setLastName("Nowak");
        newUser.setEmail("test123@test.com");

    }
}