package me.jakub.randomtp.command;

public class InvalidUsageException extends CommandExecutionException {
    public InvalidUsageException() {
        super();
    }
    public InvalidUsageException(String reason) {
        super(reason);
    }
}
