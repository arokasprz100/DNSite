package com.dnsite.utils.DTOs;

import javax.validation.ConstraintViolation;

public class ConstrainViolationDTO {

    public ConstrainViolationDTO(ConstraintViolation violation){
        setType(violation.getRootBean().getClass().getName());
        setField(violation.getPropertyPath().toString());
        setMessage(violation.getMessage());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String type;
    private Long id;
    private String field;
    private String message;
}
