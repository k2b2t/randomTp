package me.jakub.randomtp.commands;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.TeleportUtils;
import me.jakub.randomtp.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public class rtpcommand implements CommandExecutor {


    static Randomtp plugin;

    public rtpcommand(Randomtp plugin) {
        this.plugin = plugin;
    }

    Map<String, Long> cooldowns = new HashMap<String, Long>();


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            //If executed by a player
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("@everyone")) {
                    if (player.hasPermission("randomTp.rtp.everyone")) {
                        player.sendMessage(Utils.getTpEveryoneMessage());
                        for (Player target : Bukkit.getOnlinePlayers()) {
                            Location loc = TeleportUtils.generateLocation(target);
                            TeleportUtils.tp(target, loc);
                        }
                    }else{player.sendMessage(Utils.getNoPermission());}
                }
                if(!args[0].equalsIgnoreCase("@everyone")) {

                    if (player.hasPermission("randomTp.rtp.others")) {

                        //If player has rtp.others permission and specifies a target
                        Player target = Bukkit.getServer().getPlayer(args[0]);

                        if (target != null) {
                            if (target.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                                Location loc = TeleportUtils.generateLocation(target);
                                TeleportUtils.tp(target, loc);
                                player.sendMessage(Utils.getTpMessageSender(target));
                            } else {
                                player.sendMessage("§cThat player is not in the overworld!");
                            }
                        } else {
                            player.sendMessage("§cCouldn't find that player!");
                        }
                    }else{player.sendMessage(Utils.getNoPermission());}
            }

            }else{
                if (player.hasPermission("randomTp.rtp")) {

                    if (!player.hasPermission("randomTp.cooldown.bypass")) {

                        if (cooldowns.containsKey(player.getName())) {
                            // player is inside hashmap
                            if (cooldowns.get(player.getName()) > System.currentTimeMillis()) {
                                // they still have time left in the cooldown
                                long timeLeft = (cooldowns.get(player.getName()) - System.currentTimeMillis()) / 1000;
                                player.sendMessage("§6You have a cooldown of §b" + timeLeft + " §6seconds");
                                return true;
                            }
                        }

                        cooldowns.put(player.getName(), System.currentTimeMillis() + (plugin.getConfig().getInt("cooldown") * 1000));

                        // player doesn't have a cooldown
                        Location loc = TeleportUtils.generateLocation(player);
                        TeleportUtils.tp(player, loc);
                    }else {
                        //If args length is 0
                        Location loc = TeleportUtils.generateLocation(player);
                        TeleportUtils.tp(player, loc);
                    }
                }else{player.sendMessage(Utils.getNoPermission());}
            }

        }else{
            //If executed from console
            if (args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null){
                    if (target.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                        Location loc = TeleportUtils.generateLocation(target);
                        TeleportUtils.tp(target, loc);
                        System.out.println("Successfully teleported " + target.getName() + " to a random location");
                    } else {
                        System.out.println("That player is not in the overworld!");
                    }
                }else{
                    System.out.println("Couldn't find that player");
                }
            }else{
                System.out.println("Usage: rtp <player>");
            }
        }


        return true;
    }
}
