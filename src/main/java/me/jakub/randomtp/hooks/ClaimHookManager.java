package me.jakub.randomtp.hooks;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import lombok.Getter;
import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.Log;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;

public class ClaimHookManager {

    static Randomtp plugin;

    public ClaimHookManager(Randomtp plugin) {
        this.plugin = plugin;
    }

    private static boolean gpHooked = false;
    private static GriefPrevention griefPrevention = null;

    private static boolean wgHooked = false;
    private static WorldGuard worldGuard = null;

    public void initHooks() {
        gpHooked = hookGp();
        wgHooked = hookWg();
    }

    public boolean isClaimedAt(Location location) {
        return isGpClaimed(location) || isWgClaimed(location);
    }


    private boolean hookGp() {
        if (!(plugin.getConfig().getBoolean("Claim-protection.Griefprevention"))) return false;
        if (plugin.getServer().getPluginManager().getPlugin("GriefPrevention") != null) {
            Log.log(Log.LogLevel.SUCCESS, "Successfully hooked into GriefPrevention");
            griefPrevention = GriefPrevention.instance;
            return true;
        } else {
            Log.log(Log.LogLevel.ERROR, "Couldn't hook into GriefPrevention, check if you have it installed");
            return false;
        }
    }

    private boolean hookWg() {
        if (!(plugin.getConfig().getBoolean("Claim-protection.Worldguard"))) return false;
        if (plugin.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            Log.log(Log.LogLevel.SUCCESS, "Successfully hooked into WorldGuard");
            worldGuard = WorldGuard.getInstance();
            return true;
        } else {
            Log.log(Log.LogLevel.ERROR, "Couldn't hook into WorldGuard, check if you have it and WorldEdit installed");
            return false;
        }
    }


    private boolean isGpClaimed(Location location) {
        if (!(gpHooked && griefPrevention != null && plugin.getConfig().getBoolean("Claim-protection.Griefprevention")))
            return false;
        Claim claim = griefPrevention.dataStore.getClaimAt(location, true, null);
        return claim != null;
    }

    private boolean isWgClaimed(Location location) {
        boolean result = true;

        if (!(wgHooked && worldGuard != null && plugin.getConfig().getBoolean("Claim-protection.Worldguard")))
            return false;
        try {
            RegionContainer container = worldGuard.getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(location));
            result = set.size() == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !result;
    }


}
