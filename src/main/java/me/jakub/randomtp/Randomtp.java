package me.jakub.randomtp;

import me.jakub.randomtp.commands.RTPCommand;
import me.jakub.randomtp.commands.RTPCommandTabCompleter;
import me.jakub.randomtp.commands.RTPluginCommand;
import me.jakub.randomtp.commands.RTPluginCommandTabCompleter;
import me.jakub.randomtp.gui.confirmgui.ConfirmGUI;
import me.jakub.randomtp.listeners.*;
import me.jakub.randomtp.metrics.MetricsLite;
import me.jakub.randomtp.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public final class Randomtp extends JavaPlugin {

    public static final String VERSION = "2.14";

    private static Economy econ = null;

    public static boolean vaultHooked = false;


    @Override
    public void onEnable() {

        Log.log(Log.LogLevel.INFO, "Enabling RandomTP v" + VERSION);
        Log.log(Log.LogLevel.INFO, "Author: Kubajsa");
        Log.log(Log.LogLevel.INFO, "Use /rtplugin help for more info");

        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new SignEvents(this), this);
        getServer().getPluginManager().registerEvents(new CountdownEvents(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
        getServer().getPluginManager().registerEvents(new GUIEvent(this), this);

        getCommand("rtp").setExecutor(new RTPCommand(this));
        getCommand("rtp").setTabCompleter(new RTPCommandTabCompleter());
        getCommand("rtplugin").setExecutor(new RTPluginCommand(this));
        getCommand("rtplugin").setTabCompleter(new RTPluginCommandTabCompleter());

        new TeleportUtils(this);
        Utils utils = new Utils(this);
        new MetricsLite(this, 10130);
        new PlayerUtils(this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();


        if (this.getConfig().getBoolean("Vault.enabled")) {
            if (!setupEconomy()) {
                Log.log(Log.LogLevel.ERROR, "No Vault or Essentials plugin found!");
                Log.log(Log.LogLevel.ERROR, "Check if you have both Vault and Essentials installed");
                vaultHooked = false;
            } else {
                Log.log(Log.LogLevel.SUCCESS, "Successfully hooked into Vault");
                vaultHooked = true;
            }
        } else {
            vaultHooked = false;
        }

        Log.log(Log.LogLevel.SUCCESS, "Finished loading!");
        if (utils.getUpdateCheckerEnabled()) {
            Log.log(Log.LogLevel.INFO, "Checking for updates..");
        }

        if (utils.getUpdateCheckerEnabled()) {
            new UpdateChecker(this, 86659).getLatestVersion(version -> {
                if (this.VERSION.equalsIgnoreCase(version)) {
                    Log.log(Log.LogLevel.INFO, "RandomTP is up to date");
                } else {
                    Log.log(Log.LogLevel.WARNING, "A new version is available: spigotmc.org/resources/random-tp.86659/");
                    Bukkit.broadcastMessage("§c[RandomTP] §6A new version is available on SpigotMC");
                }

            });
        }

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        if (vaultHooked) {
            return econ;
        } else {
            return null;
        }
    }
}
