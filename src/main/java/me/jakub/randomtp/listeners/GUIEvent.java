package me.jakub.randomtp.listeners;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.TeleportUtils;
import me.jakub.randomtp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIEvent implements Listener {

    static Randomtp plugin;

    public GUIEvent(Randomtp plugin){
        this.plugin = plugin;
    }

    TeleportUtils teleportUtils = new TeleportUtils(plugin);

    @EventHandler
    public void guiEvent(InventoryClickEvent e) {

        if (e.getView().getTitle().equalsIgnoreCase(Utils.getConfirmGUITitle()) && Utils.isConfirmGUIEnabled()) {
            Player player = (Player) e.getView().getPlayer();
            e.setCancelled(true);//Cancel event if the player is in confirm GUI

            if (e.getCurrentItem().getType() == Material.EMERALD_BLOCK){
                //Player confirmed
                teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, true, null, false, true);
                player.closeInventory();
            }else if(e.getCurrentItem().getType() == Material.REDSTONE_BLOCK){
                //Player cancelled
                player.closeInventory();
                return;
            }
        }

    }
}
