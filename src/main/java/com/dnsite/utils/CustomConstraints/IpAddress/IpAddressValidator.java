package com.dnsite.utils.CustomConstraints.IpAddress;

import com.vladmihalcea.hibernate.type.basic.Inet;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IpAddressValidator implements ConstraintValidator<IpAddress, Inet> {

    private static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

    @Override
    public boolean isValid(Inet inet, ConstraintValidatorContext constraintValidatorContext){
        if(inet == null){
            return false;
        }

        boolean isValid =  Pattern.compile(ipv4Pattern).matcher(inet.getAddress()).matches()
                || Pattern.compile(ipv6Pattern).matcher(inet.getAddress()).matches();

        if (!isValid){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("com.dnsite.utils.CustomConstraints."+
                    "constraintvalidatorcontext.IpAddress.message: Not a valid IP Address").addConstraintViolation();
        }

        return isValid;
    }

}
