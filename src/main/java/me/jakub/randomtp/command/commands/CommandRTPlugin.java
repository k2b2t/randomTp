package me.jakub.randomtp.command.commands;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.command.CommandExecutionException;
import me.jakub.randomtp.command.InvalidUsageException;
import me.jakub.randomtp.command.NoPermissionException;
import me.jakub.randomtp.command.RandomTPCommand;
import me.jakub.randomtp.command.tabcomplete.RTPluginCommandTabCompleter;
import me.jakub.randomtp.utils.Log;
import me.jakub.randomtp.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRTPlugin extends RandomTPCommand {
    private Randomtp plugin;

    public CommandRTPlugin(Randomtp plugin) {
        super(plugin, "rtplugin");
        this.plugin = plugin;
        setTabCompleter(new RTPluginCommandTabCompleter());
    }

    private CommandSender sender;
    private Player player;
    private String[] args;

    @Override
    public void execute(CommandSender sender, String label, String[] args) throws CommandExecutionException {
        this.sender = sender;
        this.args = args;

        if (sender instanceof Player) {
            this.player = (Player) sender;
            runPlayer();
        } else
            runConsole();

    }

    //--------------RUN PLAYER-----------------
    private void runPlayer() throws CommandExecutionException {
        switch (args.length) {
            case 0:
                runPlayerNoArgs();
                break;
            case 1:
                runPlayerOneArg();
                break;
            case 2:
                runPlayerTwoArgs();
                break;
            default:
                throw new InvalidUsageException();
        }
    }

    private void runPlayerNoArgs() {
        player.sendMessage("§6You are running §bRandomTP§6 Version: §b" + Randomtp.VERSION);
        player.sendMessage("§6For help type the command §b/rtplugin help");
    }

    //TODO CHECK FOR PERMS
    private void runPlayerOneArg() throws CommandExecutionException {
        switch (args[0].toLowerCase()) {
            case "help":
                throw new InvalidUsageException("(/rtplugin help <commands|permissions>)");
            case "setborder":
                throw new InvalidUsageException("(/rtplugin setborder <number>)");
            case "reload":
                if (!player.hasPermission("randomTp.reload"))
                    throw new NoPermissionException();
                Utils.reloadConfig(player);
                break;
            default:
                throw new InvalidUsageException();
        }
    }

    private void runPlayerTwoArgs() throws CommandExecutionException {
        switch (args[0].toLowerCase()) {
            case "help":
                runPlayerHelp();
                break;
            case "setborder":
                if (!player.hasPermission("randomTp.setborder"))
                    throw new NoPermissionException();
                runPlayerSetBorder();
                break;
            default:
                throw new InvalidUsageException();
        }
    }

    private void runPlayerHelp() throws NoPermissionException {
        switch (args[1].toLowerCase()) {
            case "commands":
                for (String msg : Utils.getHelpMessages(1)) {
                    player.sendMessage(msg);
                }
                break;
            case "permissions":
                for (String msg : Utils.getHelpMessages(2)) {
                    player.sendMessage(msg);
                }
                break;
            default:
                throw new NoPermissionException();
        }
    }

    private void runPlayerSetBorder() throws CommandExecutionException {
        try {
            int i = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new CommandExecutionException("§cThat isn't a number");
        }
        int num = Integer.parseInt(args[1]);
        if (!(num <= 30000000))
            throw new CommandExecutionException("§cNumber must be < 30.000.000");
        plugin.getConfig().set("number", num);
        plugin.saveConfig();
        sender.sendMessage("§aSet border to " + num);
    }


    //--------------RUN CONSOLE----------------
    private void runConsole() throws CommandExecutionException {
        switch (args.length) {
            case 0:
                runConsoleNoArgs();
                break;
            case 1:
                runConsoleOneArg();
                break;
            case 2:
                runConsoleTwoArgs();
                break;
            default:
                throw new InvalidUsageException();
        }
    }

    private void runConsoleNoArgs() {
        Log.log(Log.LogLevel.PLAIN, "§6You are running §bRandomTP§6 Version: §b" + Randomtp.VERSION);
        Log.log(Log.LogLevel.PLAIN, "§6For help type the command §brtplugin help");
    }

    private void runConsoleOneArg() throws InvalidUsageException {
        switch (args[0].toLowerCase()) {
            case "help":
                throw new InvalidUsageException("(/rtplugin help <commands|permissions>)");
            case "setborder":
                throw new InvalidUsageException("(/rtplugin setborder <number>)");
            case "reload":
                Utils.reloadConfig();
                break;
            default:
                throw new InvalidUsageException();
        }
    }

    private void runConsoleTwoArgs() throws CommandExecutionException {
        switch (args[0].toLowerCase()) {
            case "help":
                runConsoleHelp();
                break;
            case "setborder":
                runConsoleSetBorder();
                break;
            default:
                throw new InvalidUsageException();
        }
    }

    private void runConsoleHelp() throws InvalidUsageException {
        switch (args[1].toLowerCase()) {
            case "commands":
                for (String msg : Utils.getHelpMessages(1)) {
                    Log.log(Log.LogLevel.PLAIN, msg);
                }
                break;
            case "permissions":
                for (String msg : Utils.getHelpMessages(2)) {
                    Log.log(Log.LogLevel.PLAIN, msg);
                }
                break;
            default:
                throw new InvalidUsageException("(/rtplugin help <commands|permissions>)");
        }
    }

    private void runConsoleSetBorder() throws CommandExecutionException {
        try {
            int i = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new CommandExecutionException("§cThat isn't a number");
        }
        int num = Integer.parseInt(args[1]);
        if (!(num <= 30000000))
            throw new CommandExecutionException("§cNumber must be < 30.000.000");
        plugin.getConfig().set("number", num);
        plugin.saveConfig();
        sender.sendMessage("§aSet border to " + num);
    }



    /*
     * /rtp [player|@everyone] [world] [tier|biome]
     * /rtplugin <help|setborder|reload> [number|commands|permissions]
     */
}
