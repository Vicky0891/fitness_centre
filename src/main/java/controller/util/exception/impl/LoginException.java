package controller.util.exception.impl;

import controller.util.exception.ValidationException;

public class LoginException extends ValidationException {

    public LoginException(String errorMessage) {
        super(errorMessage);
    }
}
