package controller.command;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    
    public String execute(HttpServletRequest req);

}
