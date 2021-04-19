package me.jakub.randomtp.command;

public class NoPermissionException extends CommandExecutionException{
    public NoPermissionException() {
        super();
    }
    public NoPermissionException(String reason){
        super(reason);
    }
}
