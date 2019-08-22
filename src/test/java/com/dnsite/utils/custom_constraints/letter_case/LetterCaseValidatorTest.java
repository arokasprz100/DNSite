package com.dnsite.utils.custom_constraints.letter_case;

import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;

import static org.junit.Assert.assertTrue;

public class LetterCaseValidatorTest {

    @LetterCase(LetterCaseMode.LOWER)
    private String lowerCase;

    @Before
    public void setUp() {
        lowerCase = "s";
    }

    @Test
    public void testIsValid() {

        // Run the test
        final boolean result = Validation.buildDefaultValidatorFactory().getValidator().validate(lowerCase).isEmpty();

        // Verify the results 
        assertTrue(result);
    }
}
