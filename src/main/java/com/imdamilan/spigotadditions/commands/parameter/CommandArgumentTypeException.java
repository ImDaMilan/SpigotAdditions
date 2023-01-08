package com.imdamilan.spigotadditions.commands.parameter;

public class CommandArgumentTypeException extends IllegalArgumentException {

    public CommandArgumentTypeException(String message) {
        super(message);
    }

    public CommandArgumentTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandArgumentTypeException(Throwable cause) {
        super(cause);
    }

    public CommandArgumentTypeException() {
        super();
    }
}

