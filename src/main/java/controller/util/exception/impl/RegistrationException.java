package controller.util.exception.impl;

import controller.util.exception.ValidationException;

public class RegistrationException extends ValidationException {

    public RegistrationException(String errorMessage) {
        super(errorMessage);
    }

}
