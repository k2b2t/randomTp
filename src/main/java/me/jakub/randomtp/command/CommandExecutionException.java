package me.jakub.randomtp.command;

import java.util.Optional;

public class CommandExecutionException extends Exception {
    public CommandExecutionException() {
        this(null);
    }

    public CommandExecutionException(String msg) {
        super(msg);
    }

    public Optional<String> getReason() {
        return Optional.ofNullable(getMessage());
    }
}
