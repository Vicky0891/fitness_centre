package controller.util.exception.impl;

import lombok.Data;

@Data
public class ValidationException extends RuntimeException {

    public String errorMessage;

    public ValidationException(String errorMessage, Throwable cause) {
        super(cause);
        this.errorMessage = errorMessage;
    }

    public ValidationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
