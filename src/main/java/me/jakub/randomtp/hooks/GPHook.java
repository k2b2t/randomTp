package me.jakub.randomtp.hooks;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.Utils;
import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.Location;

public class GPHook {

    public static boolean isClaimedAt(Location location){
        if (Randomtp.gpHooked && Randomtp.getGriefPrevention() != null && Utils.getDisableRTPInClaimedAreas()){
            Claim claim = Randomtp.getGriefPrevention().dataStore.getClaimAt(location, true, null);
            return claim != null;
        }else{
            return false;
        }
    }


}
