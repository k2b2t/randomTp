package me.jakub.randomtp.listeners;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.TeleportUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    static Randomtp plugin;

    public JoinEvent(Randomtp plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onFirstJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (!player.hasPlayedBefore()){
            if (plugin.getConfig().getBoolean("rtp-on-first-join")){
                Location loc = TeleportUtils.generateLocation(player);
                TeleportUtils.tp(player, loc, true, true);
            }
        }
    }
}
