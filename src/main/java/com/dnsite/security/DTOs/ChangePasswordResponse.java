package com.dnsite.security.DTOs;

public class ChangePasswordResponse {
    public boolean changed;
    public String errorMessage;

    public ChangePasswordResponse(){}

    public ChangePasswordResponse(boolean changed, String  errorMessage){
        this.changed = changed;
        this.errorMessage = errorMessage;
    }

}
