package com.dnsite.utils.DTOs;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

public class ConstraintViolationDTO {

    public static <T> Set<ConstraintViolationDTO> ofSet(Set<ConstraintViolation<T>> violations){
        return ConstraintViolationDTO.ofSet(violations, null);
    }

    public static <T> Set<ConstraintViolationDTO> ofSet(Set<ConstraintViolation<T>> violations, Long rowNumber){
        Set<ConstraintViolationDTO> violationsDTO = new HashSet<>();

        for(ConstraintViolation violation : violations){
            violationsDTO.add(new ConstraintViolationDTO(violation, rowNumber));
        }

        return violationsDTO;
    }

    public ConstraintViolationDTO(ConstraintViolation violation){
        this(violation, null);
    }

    public ConstraintViolationDTO(ConstraintViolation violation, Long rowNumber){
        setType(violation.getRootBean().getClass().getName());
        setField(violation.getPropertyPath().toString());
        setRowNumber(rowNumber);
        setMessage(violation.getMessage());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
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
    private Long rowNumber;
    private String field;
    private String message;
}
