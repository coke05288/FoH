package com.hzipyb.userservice.domain.user.exception;

public class UnAuthorizedAccessException extends RuntimeException{
    public UnAuthorizedAccessException(String message){
        super(message);
    }
}
