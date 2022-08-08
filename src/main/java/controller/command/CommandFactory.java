package controller.command;

import java.util.HashMap;
import java.util.Map;

import controller.command.impl.ErrorCommand;
import controller.command.impl.LoginCommand;
import controller.command.impl.LoginFormCommand;
import controller.command.impl.LogoutCommand;
import controller.command.impl.PrescriptionCommand;
import controller.command.impl.gym.GymmembershipCommand;
import controller.command.impl.gym.GymmembershipsCommand;
import controller.command.impl.order.AddToCartCommand;
import controller.command.impl.order.CartCommand;
import controller.command.impl.order.CreateOrderCommand;
import controller.command.impl.order.OrderCommand;
import controller.command.impl.order.OrdersByClientCommand;
import controller.command.impl.user.CreateUserCommand;
import controller.command.impl.user.CreateUserFormCommand;
import controller.command.impl.user.EditUserCommand;
import controller.command.impl.user.EditUserFormCommand;
import controller.command.impl.user.TrainerCommand;
import controller.command.impl.user.TrainersCommand;
import controller.command.impl.user.UserCommand;
import controller.util.PagingUtil;
import service.UserService;
import service.GymMembershipService;
import service.OrderService;
import service.PrescriptionService;
import service.factory.ServiceFactory;

public class CommandFactory {
    
    private static CommandFactory INSTANCE = new CommandFactory();
    private Map<String, Command> commands;

    private CommandFactory() {
        commands = new HashMap<>();
        commands.put("gymmemberships", new GymmembershipsCommand(ServiceFactory.INSTANCE.getService(GymMembershipService.class), PagingUtil.INSTANCE));
        commands.put("gymmembership", new GymmembershipCommand(ServiceFactory.INSTANCE.getService(GymMembershipService.class)));
        commands.put("trainers", new TrainersCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
        commands.put("trainer", new TrainerCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
        commands.put("user", new UserCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
        commands.put("create_user_form", new CreateUserFormCommand());
        commands.put("create_user", new CreateUserCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
        commands.put("edit_user_form", new EditUserFormCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
        commands.put("edit_user", new EditUserCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
        commands.put("orders", new OrdersByClientCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
        commands.put("order", new OrderCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
        commands.put("login_form", new LoginFormCommand());
        commands.put("login", new LoginCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
        commands.put("logout", new LogoutCommand());
        commands.put("prescription", new PrescriptionCommand(ServiceFactory.INSTANCE.getService(PrescriptionService.class)));
        commands.put("add_to_cart", new AddToCartCommand());
        commands.put("cart", new CartCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
        commands.put("create_order", new CreateOrderCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
        
        
        commands.put("error", new ErrorCommand());
        
        
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
