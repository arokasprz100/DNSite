package com.dnsite.utils.CustomConstraints;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertTrue;

public class CheckCaseValidatorTest {

    private CheckCaseValidator checkCaseValidatorUnderTest;

    @Before
    public void setUp() {
        checkCaseValidatorUnderTest = new CheckCaseValidator();
    }

    @Test
    public void testIsValid() {
        // Setup
        final String s = "s";
        final ConstraintValidatorContext constraintValidatorContext = null;
        checkCaseValidatorUnderTest.setCaseMode(CaseMode.LOWER);

        // Run the test
        final boolean result = checkCaseValidatorUnderTest.isValid(s, constraintValidatorContext);

        // Verify the results 
        assertTrue(result);
    }
}
