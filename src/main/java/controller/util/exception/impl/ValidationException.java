package controller.util.exception.impl;

import lombok.Data;

@Data
public class ValidationException extends Exception{

    public String errorMessage;
    
    public ValidationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
