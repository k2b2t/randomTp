package me.jakub.randomtp.listeners;

import me.jakub.randomtp.utils.TeleportUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class CountdownEvents implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (TeleportUtils.willTp.contains(player) && TeleportUtils.hasCountdown.contains(player)) {
            Location loc1 = e.getFrom();
            Location loc2 = e.getTo();
            if (Math.round(loc1.getX()) == Math.round(loc2.getX()) &&
                    Math.round(loc1.getY()) == Math.round(loc2.getY()) &&
                    Math.round(loc1.getZ()) == Math.round(loc2.getZ())) {

            } else {

                TeleportUtils.willTp.remove(player);
                player.sendMessage("§4Cancelled teleportation!");
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (TeleportUtils.willTp.contains(player) && TeleportUtils.hasCountdown.contains(player)) {
                TeleportUtils.willTp.remove(player);
                player.sendMessage("§4Cancelled teleportation!");
            }
        }
    }
}
