package com.dnsite.supermaster.validator;

import com.dnsite.supermaster.model.Supermaster;
import com.dnsite.supermaster.service.SupermasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SupermasterValidator implements Validator {

    private SupermasterService supermasterService;

    @Autowired
    public SupermasterValidator(SupermasterService supermasterService) {
        this.supermasterService = supermasterService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Supermaster.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Supermaster supermaster = (Supermaster) o;

        // TODO: change when IP replaced with other type
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "supermasterId.ip", "NotEmpty");
        if (supermaster.getSupermasterId().getIp().length() < 7 || supermaster.getSupermasterId().getIp().length() > 15) {
            errors.rejectValue("supermasterId.ip", "Size.supermasterForm.supermasterId.ip");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "supermasterId.nameserver", "NotEmpty");
        if (supermaster.getSupermasterId().getNameserver().length() < 6 || supermaster.getSupermasterId().getNameserver().length() > 255) {
            errors.rejectValue("supermasterId.nameserver", "Size.supermasterForm.supermasterId.nameserver");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "account", "NotEmpty");
        if (supermaster.getAccount().length() < 6 || supermaster.getAccount().length() > 40) {
            errors.rejectValue("account", "Size.supermasterForm.account");
        }

        if (supermasterService.findById(supermaster.getSupermasterId()) != null) {
            errors.rejectValue("supermasterId.ip", "Duplicate.supermasterForm.supermasterId.ip");
            errors.rejectValue("supermasterId.nameserver", "Duplicate.supermasterForm.supermasterId.nameserver");
        }
    }
}
