package utils.CustomConstraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckCaseValidator implements ConstraintValidator<CheckCase, String> {

    private CaseMode caseMode;

    @Override
    public void initialize(CheckCase constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null){
            return true;
        }

        boolean isValid;

        if(caseMode == CaseMode.UPPER){
            isValid = s.equals(s.toUpperCase());
        }
        else if (caseMode == CaseMode.LOWER){
            isValid = s.equals(s.toLowerCase());
        }
        else{
            isValid = false;
        }
        if (!isValid){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{utils.CustomConstraints."+
                    "constraintvalidatorcontext.CheckCase.message}").addConstraintViolation();
        }

        return isValid;
    }
}
