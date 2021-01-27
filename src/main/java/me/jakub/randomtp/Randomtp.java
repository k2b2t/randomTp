package me.jakub.randomtp;

import me.jakub.randomtp.commands.rtpcommand;
import me.jakub.randomtp.commands.rtpcommandTabCompleter;
import me.jakub.randomtp.commands.rtplugincommand;
import me.jakub.randomtp.commands.rtplugincommandTabCompleter;
import me.jakub.randomtp.listeners.JoinEvent;
import me.jakub.randomtp.metrics.MetricsLite;
import me.jakub.randomtp.utils.PlayerUtils;
import me.jakub.randomtp.utils.TeleportUtils;
import me.jakub.randomtp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class Randomtp extends JavaPlugin {

    public static String version = "2.4";

    @Override
    public void onEnable() {
        System.out.println("---------------------------------");
        System.out.println("Starting up Random Teleport...");
        System.out.println("Version: " + version);
        System.out.println("Author: Kubajsa");
        System.out.println("Use /rtplugin help for more info");
        System.out.println("                                 ");
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getCommand("rtp").setExecutor(new rtpcommand(this));
        getCommand("rtp").setTabCompleter(new rtpcommandTabCompleter());
        getCommand("rtplugin").setExecutor(new rtplugincommand(this));
        getCommand("rtplugin").setTabCompleter(new rtplugincommandTabCompleter());

        TeleportUtils utils = new TeleportUtils(this);
        Utils utils1 = new Utils(this);
        MetricsLite metricsLite = new MetricsLite(this, 10130);
        PlayerUtils playerUtils = new PlayerUtils(this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        System.out.println("Finished loading!");
        System.out.println("---------------------------------");
    }
}
