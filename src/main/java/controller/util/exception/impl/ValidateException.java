package controller.util.exception.impl;

import lombok.Data;

@Data
public class ValidateException extends ValidationException {

    public String page;

    public ValidateException(String errorMessage, String page) {
        super(errorMessage);
        this.page = page;
    }

}
