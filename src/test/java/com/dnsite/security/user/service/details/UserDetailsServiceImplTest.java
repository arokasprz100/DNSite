package com.dnsite.security.user.service.details;

import com.dnsite.security.user.model.User;
import com.dnsite.security.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserByUsername(){
        //given
        User user = new User();
        user.setUsername("TEST");
        user.setPassword("TEST");
        user.setRole("ADMIN");
        when(userRepository.findByUsername("TEST")).thenReturn(user);

        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername("TEST");

        //then
        assertEquals(userDetails.getUsername(), "TEST");
        assertEquals(userDetails.getPassword(), "TEST");
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

}