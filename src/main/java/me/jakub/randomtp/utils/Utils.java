package me.jakub.randomtp.utils;

import me.jakub.randomtp.Randomtp;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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

    public static String getTpMessage(){
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.tp-message"));
    }

    public static String getTpMessageSender(Player target) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.tp-message-sender")).replace("%player%", target.getName());
    }

    public static String getUnknownCommand(){
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.unknown-command"));
    }

    public static String getPlayerNotInOverMessage(){
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.player-not-in-overworld"));
    }

    public static String getNotEnoughMoneyMessage(){
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.not-enough-money"));
    }

    public static String getTookMoneyMessage(double amount){
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.took-money-message")).replace("%amount%", String.valueOf(amount));
    }

    public static String getSignCreateMessage(){
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.sign-create"));
    }

    public static String getSignRemoveMessage(){
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.sign-break"));
    }

    public static boolean getUpdateCheckerEnabled(){
        return plugin.getConfig().getBoolean("update-checker");
    }

    public static double getAmount(){
        return plugin.getConfig().getDouble("Vault.rtp-price");
    }

    public static boolean getEnabledSigns(){return plugin.getConfig().getBoolean("Signs.enabled");}


}
