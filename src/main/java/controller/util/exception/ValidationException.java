package controller.util.exception;

public class ValidationException extends Exception{

    public String errorMessage;
    
    public ValidationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
