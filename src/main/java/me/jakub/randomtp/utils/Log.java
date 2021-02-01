package me.jakub.randomtp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Log {

    public static void log(LogLevel level, String message){
        if (message == null){return;}

        switch (level){
            case ERROR:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[Randomtp] &8[&c&lERROR&r&8] &r" + message));
                break;
            case WARNING:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[Randomtp] &8[&6&lWARNING&r&8] &r" + message));
                break;
            case INFO:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[Randomtp] &8[&e&lINFO&r&8] &r" + message));
                break;
            case SUCCESS:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[Randomtp] &8[&a&lSUCCESS&r&8] &r" + message));
                break;
            case DEFAULT:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[Randomtp] " + message));
                break;
            case PLAIN:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                break;
            case OUTLINE:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r" + message));
                break;
        }
    }

    public enum LogLevel { ERROR, WARNING, INFO, SUCCESS, OUTLINE, DEFAULT, PLAIN }

}
