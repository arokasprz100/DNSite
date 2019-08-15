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
        String tmpPasswd = passwordGenerator.generate(15);
        assertTrue("wrong len: " + tmpPasswd,tmpPasswd.length() == 15);
        assertTrue("no digits: "+ tmpPasswd, tmpPasswd.matches(".*[0-9]+.*")); //useDigits
        assertTrue("no upper case: "+ tmpPasswd , tmpPasswd.matches(".*[A-Z]+.*")); // useUper
        assertTrue("no lower case: " + tmpPasswd, tmpPasswd.matches(".*[a-z]+.*")); // useLower
    }
}