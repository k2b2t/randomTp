package me.jakub.randomtp.utils;

import me.jakub.randomtp.Randomtp;

public class ConfigUpdateChecker {

    static Randomtp plugin;

    public ConfigUpdateChecker(Randomtp plugin) {
        this.plugin = plugin;
    }

    public void checkConfigVersion() {
        if (!(Randomtp.VERSION.equalsIgnoreCase(plugin.getConfig().getString("config-version"))) || !(plugin.getConfig().isSet("config-version")) && plugin.getConfig().getBoolean("config-version-checker")){
            Log.log(Log.LogLevel.WARNING, "Your config version does not seem to match your plugin version");
            Log.log(Log.LogLevel.WARNING, "Please delete the config and restart your server");
        }
    }

}
