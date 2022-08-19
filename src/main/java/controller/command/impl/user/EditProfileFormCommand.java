package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.dto.ClientDto;

public class EditProfileFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        ClientDto clientDto = (ClientDto) session.getAttribute("user");
        session.setAttribute("user", clientDto);
        return "jsp/user/editprofileform.jsp";
    }

}
