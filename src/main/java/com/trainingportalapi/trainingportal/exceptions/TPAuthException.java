package com.trainingportalapi.trainingportal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TPAuthException extends RuntimeException{
    public TPAuthException(String message){
        super(message);
    }
}
