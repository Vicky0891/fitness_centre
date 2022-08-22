package controller.util.exception.impl;

import lombok.Data;

@Data
public class NotFoundException extends ValidationException {

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
