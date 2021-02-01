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


public class rtplugincommand implements CommandExecutor {

    static Randomtp plugin;

    public rtplugincommand(Randomtp plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;

            if (args.length == 0){
                player.sendMessage("§6You are running §bRandomTP§6 Version: §b" + Randomtp.version);
                player.sendMessage("§6For help type the command §b/rtplugin help");
            }else if (args[0].equalsIgnoreCase("help")){
                player.sendMessage("§6Version: §c" + Randomtp.version);
                player.sendMessage("§3§lCommands:");
                player.sendMessage("§6/rtp - Teleports you to a random location within the border set in the config (Default: 1000)");
                player.sendMessage("§6/rtp [player] - /rtp other players");
                player.sendMessage("§6/rtp @everyone - /rtp everyone on the server");
                player.sendMessage("§6/wild - /rtp alias");
                player.sendMessage("§6/rtplugin help - Shows you this message");
                player.sendMessage("§6/rtplugin setborder - Allows you to set the border");
                player.sendMessage("§6/rtplugin reload - Reloads the config");
                player.sendMessage("§3§lPermissions:");
                player.sendMessage("§6randomTp.rtp - Allows you to use /rtp");
                player.sendMessage("§6randomTp.rtp.others - Allows you to /rtp other players");
                player.sendMessage("§6randomTp.rtp.everyone - Allows you to /rtp @everyone");
                player.sendMessage("§6randomTp.setborder - Allows you to set the rtp border");
                player.sendMessage("§6randomTp.reload - Allows you to reload the plugin");
                player.sendMessage("§6randomTp.cooldown.bypass - Allows you to bypass the cooldown");
                player.sendMessage("§3§lConfig:");
                player.sendMessage("§6Border size: §b" + plugin.getConfig().getInt("border"));
            }else if (args[0].equalsIgnoreCase("setborder")){


                if(player.hasPermission("randomTp.setborder")) {
                    if (args.length == 2) { //If player provided a number
                        try { //Checks if the arg is a number
                            Integer num = Integer.parseInt(args[1]);
                            WorldBorder worldBorder = player.getWorld().getWorldBorder();
                            int size = ((int) worldBorder.getSize() / 2);
                            if (num > size){
                                player.sendMessage("§cThat number is larger than the World Border (" + size + ")");
                            }
                            else if (num <= 30000000 && num >= 10) { //Checks if it is lower than 30mil and higher than 10
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
                }else{
                    player.sendMessage(Utils.getNoPermission());
                }


            }else if(args[0].equalsIgnoreCase("reload")){
                if (player.hasPermission("randomTp.reload")){
                    plugin.reloadConfig();
                    player.sendMessage("§bReloaded the config!");
                }else{player.sendMessage(Utils.getNoPermission());}
            }
            else{
                player.sendMessage(Utils.getUnknownCommand());
            }
        }else if(commandSender instanceof ConsoleCommandSender){
            if (args.length == 0){
                Log.log(Log.LogLevel.PLAIN, "§6You are running §bRandomTP§6 Version: §b" + Randomtp.version);
                Log.log(Log.LogLevel.PLAIN, "§6For help type the command §brtplugin help");
            }else if (args[0].equalsIgnoreCase("help")){
                Log.log(Log.LogLevel.PLAIN, "§6Version: §c" + Randomtp.version);
                Log.log(Log.LogLevel.PLAIN, "§3§lCommands:");
                Log.log(Log.LogLevel.PLAIN, "§6/rtp - Teleports you to a random location within the border set in the config (Default: 1000)");
                Log.log(Log.LogLevel.PLAIN, "§6/rtp [player] - /rtp other players");
                Log.log(Log.LogLevel.PLAIN, "§6/rtp @everyone - /rtp everyone on the server");
                Log.log(Log.LogLevel.PLAIN, "§6/wild - /rtp alias");
                Log.log(Log.LogLevel.PLAIN, "§6/rtplugin help - Shows you this message");
                Log.log(Log.LogLevel.PLAIN, "§6/rtplugin setborder - Allows you to set the border");
                Log.log(Log.LogLevel.PLAIN, "§6/rtplugin reload - Reloads the config");
                Log.log(Log.LogLevel.PLAIN, "§3§lPermissions:");
                Log.log(Log.LogLevel.PLAIN, "§6randomTp.rtp - Allows you to use /rtp");
                Log.log(Log.LogLevel.PLAIN, "§6randomTp.rtp.others - Allows you to /rtp other players");
                Log.log(Log.LogLevel.PLAIN, "§6randomTp.rtp.everyone - Allows you to /rtp @everyone");
                Log.log(Log.LogLevel.PLAIN, "§6randomTp.setborder - Allows you to set the rtp border");
                Log.log(Log.LogLevel.PLAIN, "§6randomTp.reload - Allows you to reload the plugin");
                Log.log(Log.LogLevel.PLAIN, "§6randomTp.cooldown.bypass - Allows you to bypass the cooldown");
                Log.log(Log.LogLevel.PLAIN, "§3§lConfig:");
                Log.log(Log.LogLevel.PLAIN, "§6Border size: §b" + plugin.getConfig().getInt("border"));
            }else{
                Log.log(Log.LogLevel.DEFAULT, "That command either does not exist or is player only");
            }
        }

        return true;
    }
}
