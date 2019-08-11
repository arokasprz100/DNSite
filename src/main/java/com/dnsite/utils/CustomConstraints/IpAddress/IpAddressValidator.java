package com.dnsite.utils.CustomConstraints.IpAddress;

import com.vladmihalcea.hibernate.type.basic.Inet;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IpAddressValidator implements ConstraintValidator<IpAddress, Inet> {

    private static final String IpAddressPattern =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    @Override
    public boolean isValid(Inet inet, ConstraintValidatorContext constraintValidatorContext){
        if(inet == null){
            return false;
        }

        return Pattern.compile(IpAddressPattern).matcher(inet.getAddress()).matches();
    }

}
