package controller.util.exception.impl;

import lombok.Data;

@Data
public class InternalErrorException extends ValidationException {

    public InternalErrorException(String errorMessage) {
        super(errorMessage);
    }

}
