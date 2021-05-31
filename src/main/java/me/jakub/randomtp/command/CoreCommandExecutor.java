package me.jakub.randomtp.command;

import me.jakub.randomtp.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CoreCommandExecutor implements CommandExecutor {
    private static final String NO_PERMS = Utils.getNoPermission();

    private final Map<String, RandomTPCommand> commandMap = new HashMap<>();

    public CoreCommandExecutor(RandomTPCommand... commands) {
        for (RandomTPCommand c : commands) {
            commandMap.put(c.getInternalName(), c);
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command executed, String label, String[] args) {
        RandomTPCommand command = commandMap.get(executed.getName());

        if (command == null) {
            sender.sendMessage("§cThis command hasn't been implemented yet");
            return true;
        }

        if (executed.testPermission(sender)) {
            try {
                command.execute(sender, label, args);
            } catch (InvalidUsageException e) {
                String reason = e.getReason().orElse("");
                sender.sendMessage("§cInvalid usage. " + reason);
                sender.sendMessage("§c" + executed.getUsage().replace("<command>", command.getInternalName()));
                //sender.sendMessage("§cInvalid usage. " + reason + "\n" + "§c" + executed.getUsage().replace("<command>", command.getInternalName()));
            } catch (NoPermissionException e) {
                sender.sendMessage(getPermissionMessage(executed));
            } catch (CommandExecutionException e) {
                String reason = e.getReason().orElse("§cCommand failed. No reason");
                sender.sendMessage(reason);
            } catch (Exception e) {
                sender.sendMessage("§cAn unexpected error occurred");
                e.printStackTrace();
            }
        } else {
            sender.sendMessage(getPermissionMessage(executed));
        }


        return true;
    }


    private String getPermissionMessage(Command command) {
        String msg = command.getPermissionMessage();
        return msg != null ? msg : NO_PERMS;
    }
}
