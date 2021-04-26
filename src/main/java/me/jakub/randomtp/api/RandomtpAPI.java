package me.jakub.randomtp.api;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.TeleportUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RandomtpAPI {

    private final Randomtp plugin;
    private final TeleportUtils teleportUtils;

    public RandomtpAPI(Randomtp plugin) {
        this.plugin = plugin;
        this.teleportUtils = new TeleportUtils(plugin);
    }


    /**
     * RTP a Player
     *
     * @param target Player to RTP
     * @param bypass Whether or not to bypass things like the countdown, cooldown, price etc.
     */
    public void rtpPlayer(Player target, boolean bypass) {
        if (bypass)
            teleportUtils.rtpPlayer(target, null, true, true, false, null, false, true, true, null, true, null);
        else
            teleportUtils.rtpPlayer(target, null, false, false, true, null, false, true, true, null, true, null);
    }

    /**
     * Open Confirm GUI and RTP
     *
     * @param target Player to RTP
     */
    public void rtpPlayerConfirmGUI(Player target) {
        teleportUtils.rtpPlayerAPI(target, true, false, false);
    }


    /**
     * Open World GUI and RTP
     *
     * @param target Player to RTP
     */
    public void rtpPlayerWorldGUI(Player target) {
        teleportUtils.rtpPlayerAPI(target, false, true, false);
    }

    /**
     * Open Tier GUI and RTP
     *
     * @param target Player to RTP
     */
    public void rtpPlayerTierGUI(Player target) {
        teleportUtils.rtpPlayerAPI(target, false, false, true);
    }

    /**
     * Generates a random location
     *
     * @param target Player to get the World from
     * @return Generated safe random location
     */
    public Location generateRTPLocation(Player target) {
        return teleportUtils.startGenerateLocation(target, null, target.getWorld(), null);
    }


}
