package controller.util.exception.impl;

public class BadRequestException extends ValidationException {

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}