package com.umaxcode.spring_boot_essentials_with_crud.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
