package me.jakub.randomtp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class rtpinfocommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

                player.sendMessage("§1-----§6§lRandom TP§1-----");
                player.sendMessage("§3§lInfo:");
                player.sendMessage("§6Author: §cKubajsa");
                player.sendMessage("§6Version: §c1.1");
                player.sendMessage("§7------------------");
                player.sendMessage("§3§lCommands:");
                player.sendMessage("§6/rtp - Teleports you to a random location within the borden set in the config (Default: 1000)");
                player.sendMessage("§6/rtp [player] - /rtp other players");
                player.sendMessage("§6/rtpinfo - Shows you this window");
                player.sendMessage("§7------------------");
                player.sendMessage("§3§lPermissions:");
                player.sendMessage("§6randomTp.rtp - Allows you to use /rtp");
                player.sendMessage("§6randomTp.rtpinfo - Allows you to use /rtpinfo");
                player.sendMessage("§6randomTp.rtp.others - Allows you to /rtp other players");
                player.sendMessage("§1--------------------------");

        }else{
            System.out.println("Only a player can execute this command!");
        }

        return true;
    }
}
