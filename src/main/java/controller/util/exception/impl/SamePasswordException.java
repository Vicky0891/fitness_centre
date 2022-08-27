package controller.util.exception.impl;

public class SamePasswordException extends ValidationException {

    public SamePasswordException(String errorMessage) {
        super(errorMessage);
    }
}
