package me.jakub.randomtp.commands;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.Cooldown;
import me.jakub.randomtp.utils.Log;
import me.jakub.randomtp.utils.TeleportUtils;
import me.jakub.randomtp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class RTPCommand implements CommandExecutor {


    static Randomtp plugin;

    public RTPCommand(Randomtp plugin) {
        this.plugin = plugin;
    }

    public static Map<String, Long> cooldowns = new HashMap<String, Long>();

    TeleportUtils teleportUtils = new TeleportUtils(plugin);


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (TeleportUtils.hasCountdown.contains(player)) {
                return true;
            }


            //If executed by a player
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("@everyone")) {
                    if (player.hasPermission("randomTp.rtp.everyone")) {
                        player.sendMessage(Utils.getTpEveryoneMessage());
                        for (Player target : Bukkit.getOnlinePlayers()) {

                            if (args.length == 2) {
                                World world = Utils.getWorldFromString(args[1]);
                                if (world == null) {
                                    player.sendMessage(Utils.getWrongWorldMessage());
                                    return true;
                                } else {
                                    teleportUtils.rtpPlayer(target, player, true, true, false, null, false, true, true, world, true, null);
                                }
                            } else if (args.length == 3) {
                                World world1 = Utils.getWorldFromString(args[1]);
                                if (world1 == null) {
                                    player.sendMessage(Utils.getWrongWorldMessage());
                                    return true;
                                } else {
                                    teleportUtils.rtpPlayer(target, player, true, true, false, args[2], false, true, true, world1, true, null);
                                }
                            } else {
                                teleportUtils.rtpPlayer(target, player, true, true, false, null, false, true, true, null, true, null);
                            }

                        }
                    } else {
                        player.sendMessage(Utils.getNoPermission());
                    }
                } else {

                    if (player.hasPermission("randomTp.rtp.others")) {

                        //If player has rtp.others permission and specifies a target
                        Player target = Bukkit.getServer().getPlayer(args[0]);

                        if (target != null) {

                            switch (args.length) {
                                case 2:
                                    World world = Utils.getWorldFromString(args[1]);
                                    if (world == null) {
                                        player.sendMessage(Utils.getWrongWorldMessage());
                                        return true;
                                    } else {
                                        teleportUtils.rtpPlayer(target, player, true, true, false, null, true, true, true, world, true, null);
                                        player.sendMessage(Utils.getTpMessageSender(target));
                                        return true;
                                    }
                                case 3:
                                    World world1 = Utils.getWorldFromString(args[1]);
                                    if (world1 == null) {
                                        player.sendMessage(Utils.getWrongWorldMessage());
                                        return true;
                                    }
                                    try {
                                        Biome b = Biome.valueOf(args[2].toUpperCase(Locale.ROOT));
                                        teleportUtils.rtpPlayer(target, player, true, true, false, b.toString(), true, true, true, world1, true, null);
                                        player.sendMessage(Utils.getTpMessageSender(target));
                                    }catch (Exception e){
                                        try {
                                            Utils.RTPTier tier = Utils.RTPTier.valueOf(args[2].toUpperCase(Locale.ROOT));
                                            teleportUtils.rtpPlayer(target, player, true, true, false, null, true, true, true, world1, true, tier);
                                            player.sendMessage(Utils.getTpMessageSender(target));
                                        }catch (Exception ex){
                                            player.sendMessage(Utils.getWrongTierOrBiomeNameMessage());
                                            return true;
                                        }
                                    }

                                    return true;
                                default:
                                    teleportUtils.rtpPlayer(target, player, true, true, false, null, false, true, true, null, true, null);
                                    player.sendMessage(Utils.getTpMessageSender(target));
                                    return true;
                            }
                        } else {
                            player.sendMessage("§cCouldn't find that player!");
                        }
                    } else {
                        player.sendMessage(Utils.getNoPermission());
                    }
                }

            } else {
                if (player.hasPermission("randomTp.rtp")) {

                    if (Utils.isWorldCommandDisabled(player.getWorld().getName())) {
                        player.sendMessage(Utils.getWorldDisabledMessage());
                        return true;
                    }

                    if (!player.hasPermission("randomTp.cooldown.bypass")) {
//START Cooldown
                        if (cooldowns.containsKey(player.getName())) {
                            // player is inside hashmap
                            if (cooldowns.get(player.getName()) > System.currentTimeMillis()) {
                                // they still have time left in the cooldown
                                long timeLeft = (cooldowns.get(player.getName()) - System.currentTimeMillis()) / 1000;
                                try {
                                    String formatted = Cooldown.getStr(timeLeft, Cooldown.FormatType.valueOf(plugin.getConfig().getString("Cooldown.msg-format-type")));
                                    player.sendMessage(Utils.getCooldownMessage(formatted));
                                } catch (Exception e) {
                                    Log.log(Log.LogLevel.ERROR, "Wrong message cooldown type was used in the config, use either SECONDS, MINUTES, HOURS or AUTO");
                                }
                                return true;
                            }
                        }
                        // player doesn't have a cooldown
                        //END Cooldown
                        teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, true, null, false, false, false, null, false, null);
                    } else {
                        //Has cooldown bypass perms
                        teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, false, null, false, false, false, null, false, null);
                    }
                } else {
                    player.sendMessage(Utils.getNoPermission());
                }
            }

        } else {
            if (commandSender instanceof ConsoleCommandSender) {
                //If executed from console
                if (args.length != 0) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {

                        if (args.length == 1) {
                            teleportUtils.rtpPlayer(target, null, true, true, false, null, false, true, true, null, true, null);
                            Log.log(Log.LogLevel.SUCCESS, "Teleporting player " + target.getName() + " to a random location");
                            return true;
                        } else if (args.length == 2) {
                            World world = Utils.getWorldFromString(args[1]);
                            if (world == null) {
                                Log.log(Log.LogLevel.ERROR, "Couldn't find that world");
                                return true;
                            }
                            teleportUtils.rtpPlayer(target, null, true, true, false, null, false, true, true, world, true, null);
                            Log.log(Log.LogLevel.SUCCESS, "Teleporting player " + target.getName() + " to a random location in world " + world.getName());
                            return true;
                        } else if (args.length == 3) {
                            World world = Utils.getWorldFromString(args[1]);
                            if (world == null) {
                                Log.log(Log.LogLevel.ERROR, "Couldn't find that world");
                                return true;
                            }
                            try {
                                //Right Tier name
                                Biome b = Biome.valueOf(args[2].toUpperCase(Locale.ROOT));
                                teleportUtils.rtpPlayer(target, null, true, true, false, b.toString(), false, true, true, world, true, null);
                            } catch (Exception e) {
                                //Wrong biome name
                                try {
                                    //Right Tier name
                                    Utils.RTPTier tier = Utils.RTPTier.valueOf(args[2].toUpperCase(Locale.ROOT));
                                    teleportUtils.rtpPlayer(target, null, true, true, false, null, false, true, true, world, true, tier);
                                } catch (Exception ex) {
                                    //Wrong Tier name
                                    Log.log(Log.LogLevel.ERROR, "Wrong biome or Tier name");
                                    return true;
                                }
                            }
                            Log.log(Log.LogLevel.SUCCESS, "Teleporting player " + target.getName() + " to a random location in world " + world.getName());
                            return true;
                        } else {
                            Log.log(Log.LogLevel.ERROR, "Usage: rtp <player> [world] [biome|tier]");
                        }
                    } else {
                        Log.log(Log.LogLevel.ERROR, "Couldn't find that player");
                    }
                } else {
                    Log.log(Log.LogLevel.ERROR, "Usage: rtp <player> [world] [biome|tier]");
                }
            }
        }


        return true;
    }
}
