package me.jakub.randomtp.listeners;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.TeleportUtils;
import me.jakub.randomtp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathEvent implements Listener {

    static Randomtp plugin;

    public DeathEvent(Randomtp plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onDeath(PlayerRespawnEvent e){
        Player player = e.getPlayer();
        if (Utils.getRtpOnDeath() && player.hasPermission("randomTp.rtp.onDeath")){
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    Location loc = TeleportUtils.generateLocation(player);
                    TeleportUtils.tp(player, loc, true, true);
                }
            }, 15);
        }
    }
}
