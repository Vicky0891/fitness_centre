package controller.command;

import java.util.HashMap;
import java.util.Map;

import controller.command.impl.ChangeLocaleCommand;
import controller.command.impl.ContactsCommand;
import controller.command.impl.ErrorCommand;
import controller.command.impl.LoginCommand;
import controller.command.impl.LoginFormCommand;
import controller.command.impl.LogoutCommand;
import controller.command.impl.prescription.PrescriptionCommand;
import controller.command.impl.gym.CreateGymmembershipCommand;
import controller.command.impl.gym.CreateGymmembershipFormCommand;
import controller.command.impl.gym.DeleteGymmembershipCommand;
import controller.command.impl.gym.EditGymmembershipCommand;
import controller.command.impl.gym.EditGymmembershipFormCommand;
import controller.command.impl.gym.GymmembershipCommand;
import controller.command.impl.gym.GymmembershipsCommand;
import controller.command.impl.order.AddFeedbackCommand;
import controller.command.impl.order.AddFeedbackFormCommand;
import controller.command.impl.order.AddToCartCommand;
import controller.command.impl.order.AllOrdersByTypeCommand;
import controller.command.impl.order.AllOrdersCommand;
import controller.command.impl.order.CartCommand;
import controller.command.impl.order.CreateOrderCommand;
import controller.command.impl.order.EditOrderCommand;
import controller.command.impl.order.EditOrderFormCommand;
import controller.command.impl.order.OrderCommand;
import controller.command.impl.order.OrdersCommand;
import controller.command.impl.order.RemoveFromCartCommand;
import controller.command.impl.order.RemoveOrderCommand;
import controller.command.impl.prescription.CreatePrescriptionCommand;
import controller.command.impl.prescription.CreatePrescriptionFormCommand;
import controller.command.impl.prescription.EditPrescriptionCommand;
import controller.command.impl.prescription.EditPrescriptionFormCommand;
import controller.command.impl.user.ClientsCommand;
import controller.command.impl.user.CreateUserCommand;
import controller.command.impl.user.CreateUserFormCommand;
import controller.command.impl.user.DeleteUserCommand;
import controller.command.impl.user.EditCabinetCommand;
import controller.command.impl.user.EditCabinetFormCommand;
import controller.command.impl.user.EditClientCommand;
import controller.command.impl.user.EditClientFormCommand;
import controller.command.impl.user.EditProfileCommand;
import controller.command.impl.user.EditProfileFormCommand;
import controller.command.impl.user.EditUserCommand;
import controller.command.impl.user.EditUserFormCommand;
import controller.command.impl.user.TrainerCommand;
import controller.command.impl.user.TrainersCommand;
import controller.command.impl.user.UserCommand;
import controller.command.impl.user.AllClientsByTypeCommand;
import controller.command.impl.user.AllClientsCommand;
import controller.command.impl.user.AllUsersCommand;
import controller.util.PagingUtil;
import service.UserService;
import service.ClientService;
import service.GymMembershipService;
import service.OrderService;
import service.PrescriptionService;
import service.TrainerService;
import service.factory.ServiceFactory;

public class CommandFactory {

    private static CommandFactory INSTANCE = new CommandFactory();
    private Map<String, Command> commands;

    private CommandFactory() {
        commands = new HashMap<>();
        commands.put("gymmemberships", new GymmembershipsCommand(
                ServiceFactory.INSTANCE.getService(GymMembershipService.class), PagingUtil.INSTANCE));
        commands.put("gymmembership",
                new GymmembershipCommand(ServiceFactory.INSTANCE.getService(GymMembershipService.class)));
        commands.put("create_gymmembership_form", new CreateGymmembershipFormCommand());
        commands.put("create_gymmembership",
                new CreateGymmembershipCommand(ServiceFactory.INSTANCE.getService(GymMembershipService.class)));
        commands.put("edit_gymmembership_form",
                new EditGymmembershipFormCommand(ServiceFactory.INSTANCE.getService(GymMembershipService.class)));
        commands.put("edit_gymmembership",
                new EditGymmembershipCommand(ServiceFactory.INSTANCE.getService(GymMembershipService.class)));
        commands.put("delete_gymmembership",
                new DeleteGymmembershipCommand(ServiceFactory.INSTANCE.getService(GymMembershipService.class)));

        commands.put("trainers", new TrainersCommand(ServiceFactory.INSTANCE.getService(TrainerService.class)));
        commands.put("trainer", new TrainerCommand(ServiceFactory.INSTANCE.getService(TrainerService.class)));
        commands.put("clients", new ClientsCommand(ServiceFactory.INSTANCE.getService(TrainerService.class)));
        commands.put("all_clients",
                new AllClientsCommand(ServiceFactory.INSTANCE.getService(ClientService.class), PagingUtil.INSTANCE));
        commands.put("all_clients_by_type",
                new AllClientsByTypeCommand(ServiceFactory.INSTANCE.getService(ClientService.class)));
        commands.put("user", new UserCommand());
        commands.put("delete_user", new DeleteUserCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
        commands.put("all_users",
                new AllUsersCommand(ServiceFactory.INSTANCE.getService(UserService.class), PagingUtil.INSTANCE));
        commands.put("create_user_form", new CreateUserFormCommand());
        commands.put("create_user", new CreateUserCommand(ServiceFactory.INSTANCE.getService(UserService.class)));
        commands.put("edit_user_form", new EditUserFormCommand(ServiceFactory.INSTANCE.getService(UserService.class),
                ServiceFactory.INSTANCE.getService(TrainerService.class)));
        commands.put("edit_user", new EditUserCommand(ServiceFactory.INSTANCE.getService(UserService.class),
                (ServiceFactory.INSTANCE.getService(TrainerService.class))));
        commands.put("edit_profile_form", new EditProfileFormCommand());
        commands.put("edit_profile", new EditProfileCommand(ServiceFactory.INSTANCE.getService(ClientService.class)));
        commands.put("edit_cabinet_form",
                new EditCabinetFormCommand(ServiceFactory.INSTANCE.getService(TrainerService.class)));
        commands.put("edit_cabinet", new EditCabinetCommand(ServiceFactory.INSTANCE.getService(TrainerService.class)));
        commands.put("edit_client_form",
                new EditClientFormCommand(ServiceFactory.INSTANCE.getService(ClientService.class),
                        ServiceFactory.INSTANCE.getService(TrainerService.class)));
        commands.put("edit_client", new EditClientCommand(ServiceFactory.INSTANCE.getService(ClientService.class)));

        commands.put("login_form", new LoginFormCommand());
        commands.put("login",
                new LoginCommand(ServiceFactory.INSTANCE.getService(UserService.class),
                        ServiceFactory.INSTANCE.getService(TrainerService.class),
                        ServiceFactory.INSTANCE.getService(ClientService.class)));
        commands.put("logout", new LogoutCommand());

        commands.put("prescription",
                new PrescriptionCommand(ServiceFactory.INSTANCE.getService(PrescriptionService.class)));
        commands.put("edit_prescription",
                new EditPrescriptionCommand(ServiceFactory.INSTANCE.getService(PrescriptionService.class)));
        commands.put("edit_prescription_form",
                new EditPrescriptionFormCommand(ServiceFactory.INSTANCE.getService(PrescriptionService.class)));
        commands.put("create_prescription_form", new CreatePrescriptionFormCommand());
        commands.put("create_prescription",
                new CreatePrescriptionCommand(ServiceFactory.INSTANCE.getService(PrescriptionService.class)));

        commands.put("orders", new OrdersCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
        commands.put("all_orders",
                new AllOrdersCommand(ServiceFactory.INSTANCE.getService(OrderService.class), PagingUtil.INSTANCE));
        commands.put("all_orders_by_type",
                new AllOrdersByTypeCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
        commands.put("order", new OrderCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
        commands.put("edit_order", new EditOrderCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
        commands.put("edit_order_form",
                new EditOrderFormCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
        commands.put("add_to_cart", new AddToCartCommand());
        commands.put("remove_from_cart", new RemoveFromCartCommand());
        commands.put("cart", new CartCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));
        commands.put("create_order", new CreateOrderCommand(ServiceFactory.INSTANCE.getService(OrderService.class),
                ServiceFactory.INSTANCE.getService(ClientService.class)));
        commands.put("remove_order", new RemoveOrderCommand());
        commands.put("add_feedback_form", new AddFeedbackFormCommand());
        commands.put("add_feedback", new AddFeedbackCommand(ServiceFactory.INSTANCE.getService(OrderService.class)));

        commands.put("error", new ErrorCommand());
        commands.put("change_locale", new ChangeLocaleCommand());
        commands.put("contacts", new ContactsCommand());

    }

    /**
     * Method get name of command like a key, check it available in factory and not
     * null and return command
     * 
     * @param command Name of command to get
     * @return Command object
     */
    public Command getCommand(String command) {
        Command commandInstance = commands.get(command);
        if (commandInstance == null) {
            commandInstance = commands.get("error");
        }
        return commandInstance;
    }

    /**
     * Method to get CommandFactory
     * 
     * @return singleton CommandFactory
     */
    public static CommandFactory getInstance() {
        return INSTANCE;
    }

}
