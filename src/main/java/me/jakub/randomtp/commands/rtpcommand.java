package me.jakub.randomtp.commands;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.TeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class rtpcommand implements CommandExecutor {


    static Randomtp plugin;

    public rtpcommand(Randomtp plugin) {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            //If executed by a player
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("@everyone")) {
                    if (player.hasPermission("randomTp.rtp.everyone")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tp-everyone-message")));
                        for (Player target : Bukkit.getOnlinePlayers()) {
                            Location loc = TeleportUtils.generateLocation(target);
                            TeleportUtils.tp(target, loc);
                        }
                    }else{player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("no-permission")));}
                }
                if(!args[0].equalsIgnoreCase("@everyone")) {

                    if (player.hasPermission("randomTp.rtp.others")) {

                        //If player has rtp.others permission and specifies a target
                        Player target = Bukkit.getServer().getPlayer(args[0]);

                        if (target != null) {
                            if (target.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                                Location loc = TeleportUtils.generateLocation(target);
                                TeleportUtils.tp(target, loc);
                                player.sendMessage("§6Teleported §c" + target.getName() + " §6to a random location");
                            } else {
                                player.sendMessage("§cThat player is not in the overworld!");
                            }
                        } else {
                            player.sendMessage("§cCouldn't find that player!");
                        }
                    }else{player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("no-permission")));}
            }

            }else{
                if (player.hasPermission("randomTp.rtp")) {
                    //If args length is 0
                    Location loc = TeleportUtils.generateLocation(player);
                    TeleportUtils.tp(player, loc);
                }else{player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("no-permission")));}
            }

        }else{
            //If executed from console
            System.out.println("Only a player can execute this command!");
        }


        return true;
    }
}
