package com.dnsite.config;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.MockitoAnnotations.initMocks;

public class WebSecurityConfigTest {

    @Mock
    private UserDetailsService mockUserDetailsService;

    @InjectMocks
    private WebSecurityConfig webSecurityConfigUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test(expected = Exception.class)
    public void testConfigureGlobal_ThrowsException() throws Exception {
        // Setup
        final AuthenticationManagerBuilder auth = null;

        // Run the test
        webSecurityConfigUnderTest.configureGlobal(auth);
    }

    @Test(expected = Exception.class)
    public void testAuthenticationManagerBean_ThrowsException() throws Exception {
        // Setup
        final AuthenticationManager expectedResult = null;

        // Run the test
        webSecurityConfigUnderTest.authenticationManagerBean();
    }
}
