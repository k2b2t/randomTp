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
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("no-permission"));
    }

    public static String getTpEveryoneMessage() {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tp-everyone-message"));
    }

    public static String getTpMessageSender(Player target) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("tp-message-sender")).replace("%player%", target.getName());
    }

    public static String getUnknownCommand(){
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("unknown-command"));
    }
}
