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

import java.util.Random;

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
            if (player.hasPermission("randomTp.rtp.others")) {

                if (args.length != 0) {
                    //If player has rtp.others permission and specifies a target
                        Player target = Bukkit.getServer().getPlayer(args[0]);

                        if (target != null) {
                            if (target.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                                Location loc = TeleportUtils.generateLocation(target);
                                TeleportUtils.tp(target, loc);
                                Integer x = target.getLocation().getBlockX();
                                Integer y = target.getLocation().getBlockY();
                                Integer z = target.getLocation().getBlockZ();
                                player.sendMessage("§6Teleported §c" + target.getDisplayName() + " §6to " + x.toString() + " " + y.toString() + " " + z.toString());
                            }else{
                                player.sendMessage("§cThat player is not in the overworld!");
                            }
                        }else{
                            player.sendMessage("§cCouldn't find that player!");
                        }
                }else{
                    //If player has rtp.others permission but doesn't specify a target
                    if (player.getWorld().getEnvironment().equals(World.Environment.NETHER) || player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                        player.sendMessage("§cYou can only use /rtp in the overworld!");
                    }else{
                        Location loc = TeleportUtils.generateLocation(player);
                        TeleportUtils.tp(player, loc);
                    }
                }
            }else{
                //If player doesn't have rtp.others permission
                if (player.getWorld().getEnvironment().equals(World.Environment.NETHER) || player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                    player.sendMessage("§cYou can only use /rtp in the overworld!");
                }else{
                    Location loc = TeleportUtils.generateLocation(player);
                    TeleportUtils.tp(player, loc);
                }
            }

        }else{
            //If executed from console
            System.out.println("Only a player can execute this command!");
        }


        return true;
    }
}
