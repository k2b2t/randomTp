package me.jakub.randomtp.command;

import me.jakub.randomtp.Randomtp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

public abstract class RandomTPCommand {
    private final PluginCommand wrapped;

    public RandomTPCommand(Randomtp plugin, String commandName) {
        this(plugin.getCommand(commandName));
    }

    public RandomTPCommand(PluginCommand wrapped) {
        this.wrapped = wrapped;
    }

    public String getInternalName() {
        return wrapped.getName();
    }

    public boolean wraps(Command command){
        return wrapped == command;
    }

    public void setTabCompleter(TabCompleter tabCompleter){
        wrapped.setTabCompleter(tabCompleter);
    }

    public abstract void execute(CommandSender sender, String label, String[] args) throws CommandExecutionException;
}
