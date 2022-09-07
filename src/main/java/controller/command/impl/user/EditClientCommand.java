package controller.command.impl.user;

import controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.ClientService;
import service.dto.ClientDto;
import service.dto.ClientDto.TypeDto;

@Log4j2
public class EditClientCommand implements Command {
    private final ClientService clientService;

    public EditClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long clientId = Long.parseLong(req.getParameter("id"));
        ClientDto currentClientDto = clientService.getById(clientId);
        String type = req.getParameter("type");
        String trainerId = req.getParameter("trainerId");
        currentClientDto.setType(TypeDto.valueOf(type));
        currentClientDto.setTrainerId(Long.valueOf(trainerId));

        ClientDto updated = clientService.update(currentClientDto);
        log.info("Client was update, client={}", updated);
        return "redirect:controller?command=all_clients&update= ";
    }

}
