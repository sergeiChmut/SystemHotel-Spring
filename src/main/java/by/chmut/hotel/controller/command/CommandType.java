package by.chmut.hotel.controller.command;

import by.chmut.hotel.controller.command.impl.*;

public enum CommandType {

    HOME("Home", "pages/main.jspx", new SetUniqueNumRoomCommand()),//new DefaultCommand()),

    LOGIN("Login", "",  new SetUniqueNumRoomCommand()),

    LOGOUT("Logout", "",new SetUniqueNumRoomCommand()),

    SEARCH("Search", "pages/search.jspx", new SetUniqueNumRoomCommand()),

    ADD_ACCOUNT("Add_account", "pages/autorization.jspx", new SetUniqueNumRoomCommand()),

    CREATE_USER("Create_user","", new SetUniqueNumRoomCommand()),

    RESERVATION("Reservation", "pages/reservation.jspx", new SetUniqueNumRoomCommand()),

    PAYMENT("Payment", "pages/payment.jspx", new SetUniqueNumRoomCommand()),

    ADMIN("Administration", "pages/administration.jspx", new SetUniqueNumRoomCommand()),

    SET_ROOM_ID("SetRoomId", "", new SetUniqueNumRoomCommand()),

    SET_UNIQUE_NUMBER("SetUniqueNumber", "", new SetUniqueNumRoomCommand());

    private String commandName;
    private String pagePath;
    private Command command;

    CommandType(String commandName, String pagePath, Command command) {
        this.commandName = commandName;
        this.pagePath = pagePath;
        this.command = command;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getPagePath() {
        return pagePath;
    }

    public Command getCommand() {
        return command;
    }
}
