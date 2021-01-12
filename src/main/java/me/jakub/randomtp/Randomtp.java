package me.jakub.randomtp;

import me.jakub.randomtp.commands.rtpcommand;
import me.jakub.randomtp.commands.rtplugincommand;
import me.jakub.randomtp.commands.rtplugincommandTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Randomtp extends JavaPlugin {

    public static String version = "1.8.1";

    @Override
    public void onEnable() {
        System.out.println("---------------------------------");
        System.out.println("Starting up Random Teleport...");
        System.out.println("Version: " + version);
        System.out.println("Author: Kubajsa");
        System.out.println("Use /rtplugin help for more info");
        System.out.println("                                 ");
        getCommand("rtp").setExecutor(new rtpcommand(this));
        getCommand("rtplugin").setExecutor(new rtplugincommand(this));
        getCommand("rtplugin").setTabCompleter(new rtplugincommandTabCompleter());

        TeleportUtils utils = new TeleportUtils(this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        System.out.println("Finished loading!");
        System.out.println("---------------------------------");
    }
}
