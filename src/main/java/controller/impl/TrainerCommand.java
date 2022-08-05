package controller.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import service.TrainerService;
import service.dto.TrainerDto;
import controller.command.Command;
@Log4j2
public class TrainerCommand implements Command{
 
        private TrainerService trainerService;

        public TrainerCommand(TrainerService trainerService) {
            this.trainerService = trainerService;
        }

        @Override
        public String execute(HttpServletRequest req) {
            try {
                Long id = Long.parseLong(req.getParameter("id"));
                TrainerDto trainer = trainerService.getById(id);
                req.setAttribute("trainer", trainer);
                return "jsp/trainer.jsp";
            } catch (NumberFormatException e) {
                log.error("Request isn't correct" + e);
                return "jsp/error.jsp";
            }
        }

}
