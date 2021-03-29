package me.jakub.randomtp.gui;

import me.jakub.randomtp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WorldGUI {

    boolean oneEnabled = Utils.getWorldGUIItemEnabled(1);
    boolean twoEnabled = Utils.getWorldGUIItemEnabled(2);
    boolean threeEnabled = Utils.getWorldGUIItemEnabled(3);

    public Inventory createWorldInventory(boolean openTierGUI) {
        Inventory inventory = Bukkit.createInventory(null, 27, Utils.getWorldGUITitle());

        ItemStack background = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.setDisplayName(" ");
        background.setItemMeta(backgroundMeta);

        ItemStack worldOne = new ItemStack(Utils.getWorldGUIItemMaterial(1));
        ItemMeta worldOneMeta = worldOne.getItemMeta();
        worldOneMeta.setDisplayName(Utils.getWorldGUIItemName(1));
        worldOne.setItemMeta(worldOneMeta);

        ItemStack worldTwo = new ItemStack(Utils.getWorldGUIItemMaterial(2));
        ItemMeta worldTwoMeta = worldTwo.getItemMeta();
        worldTwoMeta.setDisplayName(Utils.getWorldGUIItemName(2));
        worldTwo.setItemMeta(worldTwoMeta);

        ItemStack worldThree = new ItemStack(Utils.getWorldGUIItemMaterial(3));
        ItemMeta worldThreeMeta = worldThree.getItemMeta();
        worldThreeMeta.setDisplayName(Utils.getWorldGUIItemName(3));
        worldThree.setItemMeta(worldThreeMeta);

        initItems(inventory, background, worldOne, worldTwo, worldThree);

        return inventory;
    }

    public void openWorldGUI(Player player, boolean openTierGUI) {
        player.openInventory(createWorldInventory(openTierGUI));
    }

    private void initItems(Inventory inv, ItemStack background, ItemStack worldOne, ItemStack worldTwo, ItemStack worldThree) {
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
        inv.setItem(11, background);
        if (oneEnabled) {
            inv.setItem(12, worldOne);
        } else {
            inv.setItem(12, background);
        }
        if (twoEnabled) {
            inv.setItem(13, worldTwo);
        } else {
            inv.setItem(13, background);
        }
        if (threeEnabled) {
            inv.setItem(14, worldThree);
        } else {
            inv.setItem(14, background);
        }
        inv.setItem(15, background);
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
