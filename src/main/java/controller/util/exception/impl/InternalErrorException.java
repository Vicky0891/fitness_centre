package controller.util.exception.impl;

import controller.util.exception.ValidationException;
import lombok.Data;

@Data
public class InternalErrorException extends ValidationException {

    public InternalErrorException(String errorMessage) {
        super(errorMessage);
    }

}
