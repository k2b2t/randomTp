package me.jakub.randomtp.gui.confirmgui;

import me.jakub.randomtp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ConfirmGUI {

    public Inventory createConfirmGUI() {
        Inventory confirmGUI = Bukkit.createInventory(null, 9, Utils.getConfirmGUITitle());

        ItemStack background = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.setDisplayName(" ");
        background.setItemMeta(backgroundMeta);

        ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName("§a§lConfirm RTP");
        confirm.setItemMeta(confirmMeta);

        ItemStack cancel = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName("§c§lCancel RTP");
        cancel.setItemMeta(cancelMeta);


        ItemStack[] items = {background, background, confirm, background, background, background, cancel, background, background};
        confirmGUI.setContents(items);
        //initItems(confirmGUI, background, confirm, cancel);
        return confirmGUI;
    }

    public void openConfirmGUI(Player player) {
        player.openInventory(createConfirmGUI());
    }

    /*private void initItems(Inventory inv, ItemStack background, ItemStack confirm, ItemStack cancel) {

        inv.setItem(0, background);
        inv.setItem(0, background);
        inv.setItem(0, background);
        inv.setItem(0, background);
        inv.setItem(0, background);
        inv.setItem(0, background);
        inv.setItem(0, background);
        inv.setItem(0, background);

    }*/

}
