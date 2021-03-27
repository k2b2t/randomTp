package me.jakub.randomtp.commands;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.Log;
import me.jakub.randomtp.utils.Utils;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


public class RTPluginCommand implements CommandExecutor {

    static Randomtp plugin;

    public RTPluginCommand(Randomtp plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length == 0) {
                player.sendMessage("§6You are running §bRandomTP§6 Version: §b" + Randomtp.VERSION);
                player.sendMessage("§6For help type the command §b/rtplugin help");
            } else if (args[0].equalsIgnoreCase("help")) {
                if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("commands")) {
                        for (String msg : Utils.getHelpMessages(1)) {
                            player.sendMessage(msg);
                        }
                    } else if (args[1].equalsIgnoreCase("permissions")) {
                        for (String msg : Utils.getHelpMessages(2)) {
                            player.sendMessage(msg);
                        }
                    } else {
                        player.sendMessage("§cUsage: /rtplugin help <commands|permissions>");
                    }
                } else {
                    player.sendMessage("§cUsage: /rtplugin help <commands|permissions>");
                }
            } else if (args[0].equalsIgnoreCase("setborder")) {


                if (player.hasPermission("randomTp.setborder")) {
                    if (args.length == 2) { //If player provided a number
                        try { //Checks if the arg is a number
                            Integer num = Integer.parseInt(args[1]);
                            WorldBorder worldBorder = player.getWorld().getWorldBorder();
                            int size = ((int) worldBorder.getSize() / 2);
                            if (num > size) {
                                player.sendMessage("§cThat number is larger than the World Border (" + size + ")");
                            } else if (num <= 30000000 && num >= 10) { //Checks if it is lower than 30mil and higher than 10
                                plugin.getConfig().set("border", num);
                                plugin.saveConfig();
                                player.sendMessage("§6Set border to §b" + args[1] + "§6 blocks"); //Sets the int "blocks" to the number in the config
                            } else {
                                Player player2 = (Player) commandSender;
                                player2.sendMessage("§cThe number has to be lower than 30.000.000 and over 10 blocks!"); //Higher than 30mil or lower than 10
                            }
                        } catch (Exception e) {
                            Player player1 = (Player) commandSender;
                            player1.sendMessage("§cUsage: /rtpsetborder <10-30000000>"); //Arg is a string instead of an integer
                        }

                    } else {
                        player.sendMessage("§cYou didn't enter a valid number!"); //Either too many or none args were provided
                    }
                } else {
                    player.sendMessage(Utils.getNoPermission());
                }


            } else if (args[0].equalsIgnoreCase("reload")) {
                if (player.hasPermission("randomTp.reload")) {
                    Utils.reloadConfig(player);
                    /*plugin.reloadConfig();
                    player.sendMessage("§bReloaded the config!");*/
                } else {
                    player.sendMessage(Utils.getNoPermission());
                }
            } else {
                player.sendMessage(Utils.getUnknownCommand());
            }
        } else if (commandSender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                Log.log(Log.LogLevel.PLAIN, "§6You are running §bRandomTP§6 Version: §b" + Randomtp.VERSION);
                Log.log(Log.LogLevel.PLAIN, "§6For help type the command §brtplugin help");
            } else if (args[0].equalsIgnoreCase("help")) {
                if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("commands")) {
                        for (String msg : Utils.getHelpMessages(1)) {
                            Log.log(Log.LogLevel.PLAIN, msg);
                        }
                    } else if (args[1].equalsIgnoreCase("permissions")) {
                        for (String msg : Utils.getHelpMessages(2)) {
                            Log.log(Log.LogLevel.PLAIN, msg);
                        }
                    } else {
                        Log.log(Log.LogLevel.ERROR, "Usage: /rtplugin help <commands|permissions>");
                    }
                } else {
                    Log.log(Log.LogLevel.ERROR, "Usage: /rtplugin help <commands|permissions>");
                }
            } else if (args[0].equalsIgnoreCase("reload")) {
                Utils.reloadConfig();
            } else {
                Log.log(Log.LogLevel.DEFAULT, "That command either does not exist or is player only");
            }
        }

        return true;
    }
}
