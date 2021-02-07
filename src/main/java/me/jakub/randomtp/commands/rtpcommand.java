package me.jakub.randomtp.commands;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.hooks.VaultHook;
import me.jakub.randomtp.utils.Log;
import me.jakub.randomtp.utils.TeleportUtils;
import me.jakub.randomtp.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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
                        if (Randomtp.vaultHooked) {
                            if (VaultHook.takeMoney(player, Utils.getAmount())) {
                                Location loc = TeleportUtils.generateLocation(player);
                                TeleportUtils.tp(player, loc);
                            }
                        }else{
                            Location loc = TeleportUtils.generateLocation(player);
                            TeleportUtils.tp(player, loc);
                        }
                    }else {
                        //Has bypass perms
                        if (Randomtp.vaultHooked) {
                            if (VaultHook.takeMoney(player, Utils.getAmount())) {
                                Location loc = TeleportUtils.generateLocation(player);
                                TeleportUtils.tp(player, loc);
                            }
                        }else{
                            Location loc = TeleportUtils.generateLocation(player);
                            TeleportUtils.tp(player, loc);
                        }
                    }
                }else{player.sendMessage(Utils.getNoPermission());}
            }

        }else{
            if(commandSender instanceof ConsoleCommandSender) {
                //If executed from console
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (target.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                            Location loc = TeleportUtils.generateLocation(target);
                            TeleportUtils.tp(target, loc);
                            Log.log(Log.LogLevel.SUCCESS,"Successfully teleported " + target.getName() + " to a random location");
                        } else {
                            Log.log(Log.LogLevel.ERROR,"That player is not in the overworld!");
                        }
                    } else {
                        Log.log(Log.LogLevel.ERROR,"Couldn't find that player");
                    }
                } else {
                    Log.log(Log.LogLevel.ERROR,"Usage: rtp <player>");
                }
            }
        }


        return true;
    }
}
