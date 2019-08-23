package com.dnsite.utils.custom_constraints.letter_case;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LetterCaseValidator implements ConstraintValidator<LetterCase, String> {

    private LetterCaseMode letterCaseMode;
    @Override
    public void initialize(LetterCase constraintAnnotation) {
        this.letterCaseMode = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null){
            return true;
        }

        boolean isValid;

        if(letterCaseMode == LetterCaseMode.UPPER){
            isValid = s.equals(s.toUpperCase());
        }
        else if (letterCaseMode == LetterCaseMode.LOWER){
            isValid = s.equals(s.toLowerCase());
        }
        else{
            isValid = false;
        }
        if (!isValid){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("com.dnsite.utils.custom_constraints."+
                    "constraintvalidatorcontext.letter_case.message: Should be " + letterCaseMode.toString()).addConstraintViolation();
        }

        return isValid;
    }

}
