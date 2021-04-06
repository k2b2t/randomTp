package me.jakub.randomtp.hooks;

import lombok.Getter;
import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.Log;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;

public class ClaimHookManager {

    static Randomtp plugin;

    public ClaimHookManager(Randomtp plugin){
        this.plugin = plugin;
    }

    @Getter
    private static boolean gpHooked = false;
    private static GriefPrevention griefPrevention = null;


    public void initHooks(){
        gpHooked = hookGp();
    }

    public boolean isClaimedAt(Location location){
        return isGpClaimed(location);
    }


    private boolean hookGp(){
        if (plugin.getConfig().getBoolean("Claim-protection.Griefprevention")) {
            if (plugin.getServer().getPluginManager().getPlugin("GriefPrevention") != null) {
                Log.log(Log.LogLevel.SUCCESS, "Successfully hooked into GriefPrevention");
                griefPrevention = GriefPrevention.instance;
                return true;
            } else {
                Log.log(Log.LogLevel.ERROR, "Couldn't hook into GriefPrevention, check if you have it installed");
                return false;
            }
        } else {
            return false;
        }
    }


    private boolean isGpClaimed(Location location){
        if (gpHooked && griefPrevention != null && plugin.getConfig().getBoolean("Claim-protection.Griefprevention")){
            Claim claim = griefPrevention.dataStore.getClaimAt(location, true, null);
            return claim != null;
        }else{
            return false;
        }
    }


}
