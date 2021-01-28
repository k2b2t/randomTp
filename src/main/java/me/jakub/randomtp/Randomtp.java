package me.jakub.randomtp;

import me.jakub.randomtp.commands.rtpcommand;
import me.jakub.randomtp.commands.rtpcommandTabCompleter;
import me.jakub.randomtp.commands.rtplugincommand;
import me.jakub.randomtp.commands.rtplugincommandTabCompleter;
import me.jakub.randomtp.listeners.JoinEvent;
import me.jakub.randomtp.metrics.MetricsLite;
import me.jakub.randomtp.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class Randomtp extends JavaPlugin {

    public static String version = "2.4";

    @Override
    public void onEnable() {

        Log.log(Log.LogLevel.OUTLINE, "---------------------------------");
        Log.log(Log.LogLevel.INFO, "Starting up Random Teleport...");
        Log.log(Log.LogLevel.INFO, "Version: " + version);
        Log.log(Log.LogLevel.INFO, "Author: Kubajsa");
        Log.log(Log.LogLevel.INFO, "Use /rtplugin help for more info");

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

        Log.log(Log.LogLevel.SUCCESS, "Finished loading!");
        Log.log(Log.LogLevel.INFO, "Checking for updates..");



        new UpdateChecker(this, 86659).getLatestVersion(version -> {
            if(this.version.equalsIgnoreCase(version)) {
                Log.log(Log.LogLevel.INFO, "RandomTP is up to date");
            } else {
                Log.log(Log.LogLevel.WARNING, "RandomTP has a newer version available: spigotmc.org/resources/random-tp.86659/");
                Bukkit.broadcastMessage("§6RandomTP has a newer version available");
            }

        });


        Log.log(Log.LogLevel.OUTLINE, "---------------------------------");

    }
}
