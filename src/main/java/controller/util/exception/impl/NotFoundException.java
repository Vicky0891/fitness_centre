package controller.util.exception.impl;

import controller.util.exception.ValidationException;
import lombok.Data;

@Data
public class NotFoundException extends ValidationException {

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
