package controller.util.exception.impl;

import controller.util.exception.ValidationException;

public class BadRequestException extends ValidationException {

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}