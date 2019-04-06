package com.dnsite.user;

import com.dnsite.security.user.model.User;
import com.dnsite.security.user.service.UserService;
import com.dnsite.security.user.validator.UserValidator;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserValidatorTest {
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
    public void userAlreadyExists() {
        when(userServiceMock.findByUsername("test123")).thenReturn(new User());

        User duplicateUser = new User();
        duplicateUser.setId(1L);
        duplicateUser.setUsername("test123");
        duplicateUser.setPassword("test123456");
        duplicateUser.setPasswordConfirm("test123456");
        duplicateUser.setFirstName("Jakub");
        duplicateUser.setLastName("Nowak");

        Errors errors = new BeanPropertyBindingResult(duplicateUser, "duplicateUser");
        log.debug(errors + " " + duplicateUser);
        userValidator.validate(duplicateUser, errors);

        FieldError usernameFieldError = errors.getFieldError("username");
        assertNotNull(usernameFieldError);
        assertNotNull(usernameFieldError.getCodes());
        assertThat("username", is(usernameFieldError.getField()));
        List<String> errorCodes = Arrays.asList(usernameFieldError.getCodes());
        assertThat(4, is(errorCodes.size()));
        assertTrue(errorCodes.contains("Duplicate.userForm.username.duplicateUser.username"));
        assertTrue(errorCodes.contains("Duplicate.userForm.username.username"));
        assertTrue(errorCodes.contains("Duplicate.userForm.username.java.lang.String"));
        assertTrue(errorCodes.contains("Duplicate.userForm.username"));
    }
}
