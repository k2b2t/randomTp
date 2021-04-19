package me.jakub.randomtp.api;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.TeleportUtils;
import org.bukkit.entity.Player;

public class RandomtpAPI {

    private final Randomtp plugin;
    private final TeleportUtils teleportUtils;

    public RandomtpAPI(Randomtp plugin) {
        this.plugin = plugin;
        this.teleportUtils = new TeleportUtils(plugin);
    }

    /**
     * Teleports the player to a random position and bypasses
     * countdown, cooldown, and price
     *
     * @param target Player to teleport
     */
    public void rtpPlayerBypass(Player target) {
        teleportUtils.rtpPlayer(target, null, true, true, false, null, false, true, true, null, true, null);
    }

    /**
     * Teleports the player to a random position
     *
     * @param target Player to teleport
     */
    public void rtpPlayerNoBypass(Player target) {
        teleportUtils.rtpPlayer(target, null, false, false, true, null, false, true, true, null, true, null);
    }

}
