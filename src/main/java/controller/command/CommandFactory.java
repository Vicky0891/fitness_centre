package controller.command;

import java.util.HashMap;
import java.util.Map;

import controller.impl.CustomerCommand;
import controller.impl.GymMembershipCommand;
import controller.impl.TrainerCommand;
import controller.impl.TrainersCommand;
import service.CustomerService;
import service.GymMembershipService;
import service.TrainerService;
import service.factory.ServiceFactory;

public class CommandFactory {
    
    private static CommandFactory INSTANCE = new CommandFactory();
    private Map<String, Command> commands;

    private CommandFactory() {
        commands = new HashMap<>();
        commands.put("gymmemberships", new GymMembershipCommand(ServiceFactory.INSTANCE.getService(GymMembershipService.class)));
        commands.put("trainers", new TrainersCommand(ServiceFactory.INSTANCE.getService(TrainerService.class)));
        commands.put("trainer", new TrainerCommand(ServiceFactory.INSTANCE.getService(TrainerService.class)));
        commands.put("customer", new CustomerCommand(ServiceFactory.INSTANCE.getService(CustomerService.class)));
        
    }

    public Command getCommand(String command) {
        Command commandInstance = commands.get(command);
        if (commandInstance == null) {
            commandInstance = commands.get("error");
        }
        return commandInstance;
    }

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

}
