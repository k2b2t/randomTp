package me.jakub.randomtp.commands;

import me.jakub.randomtp.TeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class rtpcommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;

            if (player.hasPermission("randomTp.rtp.others")) {

                if (args.length != 0) {


                    if (player.getWorld().getEnvironment().equals(World.Environment.NETHER) || player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                        player.sendMessage("§cYou can only use /rtp in the overworld");
                    } else {
                        Player target = Bukkit.getServer().getPlayer(args[0]);
                        target.teleport(TeleportUtils.generateLocation(target));
                        target.sendMessage("§6Teleported " + ChatColor.RED + target.getDisplayName() + " §6to a random location");
                        Integer x = target.getLocation().getBlockX();
                        Integer y = target.getLocation().getBlockY();
                        Integer z = target.getLocation().getBlockZ();
                        player.sendMessage("§6Teleported §c" + target.getDisplayName() + " §6to " + x.toString() + " " + y.toString() + " " + z.toString());
                    }
                }else{
                    if (player.getWorld().getEnvironment().equals(World.Environment.NETHER) || player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                        player.sendMessage("§cYou can only use /rtp in the overworld");
                    }else{
                        player.teleport(TeleportUtils.generateLocation(player));
                        player.sendMessage(("§6Teleported " + ChatColor.RED + player.getDisplayName() + " §6to a random location"));
                    }
                }
            }else{
                if (player.getWorld().getEnvironment().equals(World.Environment.NETHER) || player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                    player.sendMessage("§cYou can only use /rtp in the overworld");
                }else{
                    player.teleport(TeleportUtils.generateLocation(player));
                    player.sendMessage(("§6Teleported " + ChatColor.RED + player.getDisplayName() + " §6to a random location"));
                }
            }

        }else{
            System.out.println("Only a player can execute this command!");
        }


        return true;
    }
}
