package me.jakub.randomtp;

import me.jakub.randomtp.api.RandomtpAPI;
import me.jakub.randomtp.command.CoreCommandExecutor;
import me.jakub.randomtp.command.commands.CommandRTP;
import me.jakub.randomtp.command.commands.CommandRTPlugin;
import me.jakub.randomtp.hooks.ClaimHookManager;
import me.jakub.randomtp.listeners.*;
import me.jakub.randomtp.metrics.MetricsLite;
import me.jakub.randomtp.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class Randomtp extends JavaPlugin {

    public static final String VERSION = "2.19.2";

    private static Economy econ = null;

    public static boolean vaultHooked = false;

    private final TeleportUtils teleportUtils = new TeleportUtils(this);
    private final Utils utils = new Utils(this);
    private final MetricsLite metricsLite = new MetricsLite(this, 10130);
    private final PlayerUtils playerUtils = new PlayerUtils(this);
    private final ClaimHookManager claimHookManager = new ClaimHookManager(this);
    private final ConfigUpdateChecker configUpdateChecker = new ConfigUpdateChecker(this);
    private static Randomtp plugin;
    private RandomtpAPI api;

    @Override
    public void onEnable() {
        plugin = this;
        api = new RandomtpAPI(plugin);


        Log.log(Log.LogLevel.INFO, "Enabling RandomTP v" + VERSION);
        Log.log(Log.LogLevel.INFO, "Author: Kubajsa");
        Log.log(Log.LogLevel.INFO, "Use /rtplugin help for more info");

        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new SignEvents(this), this);
        getServer().getPluginManager().registerEvents(new CountdownEvents(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
        getServer().getPluginManager().registerEvents(new GUIEvent(this), this);

        registerCommands();

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
        } else vaultHooked = false;


        claimHookManager.initHooks(); //Initialize all the claim hooks

        Log.log(Log.LogLevel.SUCCESS, "Finished loading!");

        configUpdateChecker.checkConfigVersion();

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

    private void registerCommands() {
        CommandExecutor executor = new CoreCommandExecutor(
                new CommandRTP(this),
                new CommandRTPlugin(this)
        );

        getDescription()
                .getCommands()
                .keySet()
                .stream().map(this::getCommand)
                .filter(Objects::nonNull)
                .forEach(c -> c.setExecutor(executor));
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

    public static Randomtp get() {
        return plugin;
    }

    public RandomtpAPI getAPI() {
        return api;
    }
}
