package me.jakub.randomtp.utils;

import me.jakub.randomtp.Randomtp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;

public class Utils {

    static Randomtp plugin;

    public Utils(Randomtp plugin) {
        this.plugin = plugin;
    }

    public static String getNoPermission() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.no-permission"));
    }

    public static String getTpEveryoneMessage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.tp-everyone-message"));
    }

    public static String getTpMessage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.tp-message"));
    }

    public static String getTpMessageSender(Player target) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.tp-message-sender")).replace("%player%", target.getName());
    }

    public static String getTpMessageSenderBiome(Player target, Biome biome) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.tp-message-sender-biome")).replace("%player%", target.getName()).replace("%biome%", biome.name().toLowerCase(Locale.ROOT));
    }

    public static String getUnknownCommand() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.unknown-command"));
    }

    public static String getNotEnoughMoneyMessage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.not-enough-money"));
    }

    public static String getTookMoneyMessage(double amount) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.took-money-message")).replace("%amount%", String.valueOf(amount));
    }

    public static String getSignCreateMessage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.sign-create"));
    }

    public static String getSignRemoveMessage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.sign-break"));
    }

    public static String getCooldownMessage(String timeLeft) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.cooldown-message")).replace("%left%", String.valueOf(timeLeft));
    }

    public static String getWorldDisabledMessage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.world-disabled-message"));
    }

    public static String getCouldntGenerateMessage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.couldnt-generate-message"));
    }

    public static String getNotShiftMessage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.need-to-shift"));
    }

    public static String getCountdownMessage(int baseSec) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.countdown-message")).replace("%count%", String.valueOf(baseSec));
    }

    public static String getCountingDownMessage(int i) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.counting-down-message")).replace("%time%", String.valueOf(i));
    }

    public static String RTPCancelledMessage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.teleportation-cancelled-message"));
    }

    public static boolean getForceDefaultWorldEnabled() {
        return plugin.getConfig().getBoolean("Force-default-world.enabled");
    }

    public static World forcedWorld(Player player) {
        try {
            World world = Bukkit.getWorld(plugin.getConfig().getString("Force-default-world.world-name"));
            if (world != null) {
                return world;
            } else {
                Log.log(Log.LogLevel.ERROR, "Wrong Force Default World world name was used in the config!!! Turning off force world");
                return player.getWorld();
            }
        } catch (Exception e) {
            Log.log(Log.LogLevel.ERROR, "Wrong Force Default World world name was used in the config!!! Turning off force world");
            return player.getWorld();
        }
    }

    public static void reloadConfig() {
        plugin.reloadConfig();
        Log.log(Log.LogLevel.SUCCESS, "Reloaded the config!");
    }

    public static void reloadConfig(Player player) {
        plugin.reloadConfig();
        player.sendMessage("§bReloaded the config!");
    }

    public static String getHour() {
        return plugin.getConfig().getString("Cooldown.Messages.hour");
    }

    public static String getMinute() {
        return plugin.getConfig().getString("Cooldown.Messages.minute");
    }

    public static String getSecond() {
        return plugin.getConfig().getString("Cooldown.Messages.second");
    }

    public static boolean getBiomeBlacklistEnabled() {
        return plugin.getConfig().getBoolean("Biome-blacklist.enabled");
    }

    public static List<String> getBiomes() {
        return (List<String>) plugin.getConfig().getList("Biome-blacklist.biomes");
    }

    public static String getConfirmGUITitle() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Rtp-settings.Confirm-gui.title"));
    }

    public static boolean isConfirmGUIEnabled() {
        return plugin.getConfig().getBoolean("Rtp-settings.Confirm-gui.enabled");
    }

    public static Material getConfirmItem() {
        try {
            return Material.valueOf(plugin.getConfig().getString("Rtp-settings.Confirm-gui.confirm-item").toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            Log.log(Log.LogLevel.ERROR, "Wrong confirm item name was used in the config!");
            return Material.EMERALD_BLOCK;
        }
    }

    public static Material getCancelItem() {
        try {
            return Material.valueOf(plugin.getConfig().getString("Rtp-settings.Confirm-gui.cancel-item").toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            Log.log(Log.LogLevel.ERROR, "Wrong cancel item name was used in the config!");
            return Material.REDSTONE_BLOCK;
        }
    }

    public static String getWorldGUITitle() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Rtp-settings.World-gui.gui-title"));
    }

    public static boolean getWorldGUIEnabled() {
        return plugin.getConfig().getBoolean("Rtp-settings.World-gui.enabled");
    }

    public static boolean getWorldGUIItemEnabled(int index){
        switch (index){
            case 1:
                return plugin.getConfig().getBoolean("Rtp-settings.World-gui.Slots.one.enabled");
            case 2:
                return plugin.getConfig().getBoolean("Rtp-settings.World-gui.Slots.two.enabled");
            case 3:
                return plugin.getConfig().getBoolean("Rtp-settings.World-gui.Slots.three.enabled");
            default:
                return false;
        }
    }

    public static String getWorldGUIItemName(int index){
        switch (index){
            case 1:
                return plugin.getConfig().getString("Rtp-settings.World-gui.Slots.one.title");
            case 2:
                return plugin.getConfig().getString("Rtp-settings.World-gui.Slots.two.title");
            case 3:
                return plugin.getConfig().getString("Rtp-settings.World-gui.Slots.three.title");
            default:
                return "§cITEM NAME NOT SET";
        }
    }

    public static World getWorldGUIItemWorld(int index){
        switch (index){
            case 1:
                return getWorldFromString(plugin.getConfig().getString("Rtp-settings.World-gui.Slots.one.world-name"));
            case 2:
                return getWorldFromString(plugin.getConfig().getString("Rtp-settings.World-gui.Slots.two.world-name"));
            case 3:
                return getWorldFromString(plugin.getConfig().getString("Rtp-settings.World-gui.Slots.three.world-name"));
            default:
                return null;
        }
    }


    public static Material getWorldGUIItemMaterial(int index){
        try {
            switch (index){
                case 1:
                    return Material.valueOf(plugin.getConfig().getString("Rtp-settings.World-gui.Slots.one.world-name"));
                case 2:
                    return Material.valueOf(plugin.getConfig().getString("Rtp-settings.World-gui.Slots.two.world-name"));
                case 3:
                    return Material.valueOf(plugin.getConfig().getString("Rtp-settings.World-gui.Slots.three.world-name"));
                default:
                    return Material.GRASS_BLOCK;
            }
        }catch (Exception e){
            return Material.GRASS_BLOCK;
        }

    }



    private static World getWorldFromString(String worldName){
        try {
            World world = Bukkit.getWorld(worldName);
            if (world != null){
                return world;
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

    public static int getCountdown() {
        return plugin.getConfig().getInt("Countdown.seconds");
    }

    //TODO block blacklist maybe?
    /*public static List<String> getBlocks() {
        return (List<String>) plugin.getConfig().getList("Block-blacklist.blocks");
    }

    public static boolean getBlockBlacklistEnabled() {
        return plugin.getConfig().getBoolean("Block-blacklist.enabled");
    }*/

    public static String getSignLine(int index) {
        switch (index) {
            case 0:
                return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Signs.Lines.one"));
            case 1:
                return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Signs.Lines.two"));
            case 2:
                return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Signs.Lines.three"));
            case 3:
                return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Signs.Lines.four"));
            default:
                return "";
        }
    }

    public static boolean isWorldSet(World world) {
        return (plugin.getConfig().isSet("Worlds." + world.getName() + ".border"));
    }

    public static int getBorderForWorld(String worldName) {
        return plugin.getConfig().getInt("Worlds." + worldName + ".border");
    }

    public static boolean isWorldDisabled(String worldName) {
        return plugin.getConfig().getBoolean("Worlds." + worldName + ".disabled");
    }

    public static int getMaxAttempts() {
        return plugin.getConfig().getInt("Rtp-settings.max-attempts");
    }

    public static boolean getRtpOnDeath() {
        return plugin.getConfig().getBoolean("rtp-on-death");
    }

    public boolean getUpdateCheckerEnabled() {
        return plugin.getConfig().getBoolean("update-checker");
    }

    public static double getAmount() {
        return plugin.getConfig().getDouble("Vault.rtp-price");
    }

    public static boolean getEnabledSigns() {
        return plugin.getConfig().getBoolean("Signs.enabled");
    }

    public static boolean getCountdownMessageEnabled() {
        return plugin.getConfig().getBoolean("Countdown.countdown-message");
    }

    public static String getWrongBiomeMessage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.wrong-biome-name"));
    }

}
