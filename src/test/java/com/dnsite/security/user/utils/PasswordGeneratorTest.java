package com.dnsite.security.user.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class PasswordGeneratorTest {

    @Test
    public void testPasswordGenerator(){

        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        String tmpPasswd = passwordGenerator.generate(7);
        assertTrue(tmpPasswd.length() == 7);
        assertTrue(tmpPasswd.matches(".*[0-9]+.*")); //useDigits
        assertTrue(tmpPasswd.matches(".*[A-Z]+.*")); // useUper
        assertTrue(tmpPasswd.matches(".*[a-z]+.*")); // useLower

    }
}