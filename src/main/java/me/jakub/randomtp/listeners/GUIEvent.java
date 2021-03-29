package me.jakub.randomtp.listeners;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.gui.TierGUI;
import me.jakub.randomtp.utils.TeleportUtils;
import me.jakub.randomtp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIEvent implements Listener {

    static Randomtp plugin;

    public GUIEvent(Randomtp plugin) {
        this.plugin = plugin;
    }

    TeleportUtils teleportUtils = new TeleportUtils(plugin);

    @EventHandler
    public void guiEvent(InventoryClickEvent e) {

        if (e.getCurrentItem() == null) return; //Prevent nullpointerexception when clicking empty slots
        if (e.getView().getTitle().equalsIgnoreCase(Utils.getConfirmGUITitle()) && Utils.isConfirmGUIEnabled()) {
            Player player = (Player) e.getView().getPlayer();
            e.setCancelled(true);//Cancel event if the player is in confirm GUI

            if (e.getCurrentItem().getType() == Utils.getConfirmItem()) {
                //Player confirmed
                player.closeInventory();
                player.updateInventory();
                teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, true, null, false, true, true, null, true, null);
            } else if (e.getCurrentItem().getType() == Utils.getCancelItem()) {
                //Player cancelled
                player.closeInventory();
                player.updateInventory();
                return;
            }
        } else if (e.getView().getTitle().equalsIgnoreCase(Utils.getWorldGUITitle()) && Utils.getWorldGUIEnabled()) {
            Player player = (Player) e.getView().getPlayer();
            e.setCancelled(true);
            TierGUI tierGUI = new TierGUI();
            boolean openTierGUI = Utils.getTierGUIEnabled();


            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.getWorldGUIItemName(1))) {
                player.closeInventory();
                player.updateInventory();
                if (!openTierGUI) {
                    teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, true, null, false, true, true, Utils.getWorldGUIItemWorld(1), true, null);
                } else {
                    tierGUI.openTierGUI(player, Utils.getWorldGUIItemWorld(1));
                }
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.getWorldGUIItemName(2))) {
                player.closeInventory();
                player.updateInventory();
                if (!openTierGUI) {
                    teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, true, null, false, true, true, Utils.getWorldGUIItemWorld(2), true, null);
                } else {
                    tierGUI.openTierGUI(player, Utils.getWorldGUIItemWorld(2));
                }
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.getWorldGUIItemName(3))) {
                player.closeInventory();
                player.updateInventory();
                if (!openTierGUI) {
                    teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, true, null, false, true, true, Utils.getWorldGUIItemWorld(3), true, null);
                } else {
                    tierGUI.openTierGUI(player, Utils.getWorldGUIItemWorld(3));
                }
            }

        } else if (e.getView().getTitle().equalsIgnoreCase(Utils.getTierGUITitle()) && Utils.getTierGUIEnabled()) {
            Player player = (Player) e.getView().getPlayer();
            World world = Bukkit.getWorld(e.getView().getTopInventory().getItem(4).getItemMeta().getDisplayName());
            e.setCancelled(true);

            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.getTierItemName(1))) {
                player.closeInventory();
                player.updateInventory();
                teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, true, null, false, true, true, world, true, Utils.RTPTier.ONE);
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.getTierItemName(2))) {
                player.closeInventory();
                player.updateInventory();
                teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, true, null, false, true, true, world, true, Utils.RTPTier.TWO);
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.getTierItemName(3))) {
                player.closeInventory();
                player.updateInventory();
                teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, true, null, false, true, true, world, true, Utils.RTPTier.THREE);
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.getTierItemName(4))) {
                player.closeInventory();
                player.updateInventory();
                teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, true, null, false, true, true, world, true, Utils.RTPTier.FOUR);
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.getTierItemName(5))) {
                player.closeInventory();
                player.updateInventory();
                teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, true, null, false, true, true, world, true, Utils.RTPTier.FIVE);
            }

        }
    }
}
