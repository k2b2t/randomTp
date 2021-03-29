package me.jakub.randomtp.gui;

import me.jakub.randomtp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfirmGUI {

    public Inventory createConfirmGUI() {
        Inventory confirmGUI = Bukkit.createInventory(null, 27, Utils.getConfirmGUITitle());

        ItemStack background = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.setDisplayName(" ");
        background.setItemMeta(backgroundMeta);

        ItemStack confirm = new ItemStack(Utils.getConfirmItem());
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName("§a§lConfirm RTP");
        confirm.setItemMeta(confirmMeta);

        ItemStack cancel = new ItemStack(Utils.getCancelItem());
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName("§c§lCancel RTP");
        cancel.setItemMeta(cancelMeta);

        initItems(confirmGUI, background, confirm, cancel);
        return confirmGUI;
    }

    public void openConfirmGUI(Player player) {
        player.openInventory(createConfirmGUI());
    }

    private void initItems(Inventory inv, ItemStack background, ItemStack confirm, ItemStack cancel) {

        inv.setItem(0, background);
        inv.setItem(1, background);
        inv.setItem(2, background);
        inv.setItem(3, background);
        inv.setItem(4, background);
        inv.setItem(5, background);
        inv.setItem(6, background);
        inv.setItem(7, background);
        inv.setItem(8, background);
        inv.setItem(9, background);
        inv.setItem(10, background);
        inv.setItem(11, confirm);
        inv.setItem(12, background);
        inv.setItem(13, background);
        inv.setItem(14, background);
        inv.setItem(15, cancel);
        inv.setItem(16, background);
        inv.setItem(17, background);
        inv.setItem(18, background);
        inv.setItem(19, background);
        inv.setItem(20, background);
        inv.setItem(21, background);
        inv.setItem(22, background);
        inv.setItem(23, background);
        inv.setItem(24, background);
        inv.setItem(25, background);
        inv.setItem(26, background);

    }

}
