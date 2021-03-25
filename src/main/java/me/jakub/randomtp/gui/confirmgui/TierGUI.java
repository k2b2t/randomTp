package me.jakub.randomtp.gui.confirmgui;

import me.jakub.randomtp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TierGUI {

    private boolean tierOneEnabled = Utils.getTierItemEnabled(1);
    private boolean tierTwoEnabled = Utils.getTierItemEnabled(2);
    private boolean tierThreeEnabled = Utils.getTierItemEnabled(3);
    private boolean tierFourEnabled = Utils.getTierItemEnabled(4);
    private boolean tierFiveEnabled = Utils.getTierItemEnabled(5);


    public Inventory createTierInventory(World targetWorld) {
        Inventory tierInv = Bukkit.createInventory(null, 27, Utils.getTierGUITitle());

        ItemStack background = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.setDisplayName(" ");
        background.setItemMeta(backgroundMeta);

        ItemStack tierOne = new ItemStack(Utils.getTierItemMaterial(1));
        ItemMeta tierOneMeta = tierOne.getItemMeta();
        tierOneMeta.setDisplayName(Utils.getTierItemName(1));
        List<String> oneLore = new ArrayList<>();
        oneLore.add(ChatColor.GREEN + "Border: " + Utils.getTierItemBorder(1));
        oneLore.add(ChatColor.GREEN + "Price: $" + Utils.getTierItemPrice(1));
        tierOneMeta.setLore(oneLore);
        tierOne.setItemMeta(tierOneMeta);

        ItemStack tierTwo = new ItemStack(Utils.getTierItemMaterial(2));
        ItemMeta tierTwoMeta = tierTwo.getItemMeta();
        List<String> twoLore = new ArrayList<>();
        twoLore.add(ChatColor.GREEN + "Border: " + Utils.getTierItemBorder(2));
        twoLore.add(ChatColor.GREEN + "Price: $" + Utils.getTierItemPrice(2));
        tierTwoMeta.setLore(twoLore);
        tierTwoMeta.setDisplayName(Utils.getTierItemName(2));
        tierTwo.setItemMeta(tierTwoMeta);

        ItemStack tierThree = new ItemStack(Utils.getTierItemMaterial(3));
        ItemMeta tierThreeMeta = tierThree.getItemMeta();
        List<String> threeLore = new ArrayList<>();
        threeLore.add(ChatColor.GREEN + "Border: " + Utils.getTierItemBorder(3));
        threeLore.add(ChatColor.GREEN + "Price: $" + Utils.getTierItemPrice(3));
        tierThreeMeta.setLore(threeLore);
        tierThreeMeta.setDisplayName(Utils.getTierItemName(3));
        tierThree.setItemMeta(tierThreeMeta);

        ItemStack tierFour = new ItemStack(Utils.getTierItemMaterial(4));
        ItemMeta tierFourMeta = tierFour.getItemMeta();
        List<String> fourLore = new ArrayList<>();
        fourLore.add(ChatColor.GREEN + "Border: " + Utils.getTierItemBorder(4));
        fourLore.add(ChatColor.GREEN + "Price: $" + Utils.getTierItemPrice(4));
        tierFourMeta.setLore(fourLore);
        tierFourMeta.setDisplayName(Utils.getTierItemName(4));
        tierFour.setItemMeta(tierFourMeta);

        ItemStack tierFive = new ItemStack(Utils.getTierItemMaterial(5));
        ItemMeta tierFiveMeta = tierFive.getItemMeta();
        List<String> fiveLore = new ArrayList<>();
        fiveLore.add(ChatColor.GREEN + "Border: " + Utils.getTierItemBorder(5));
        fiveLore.add(ChatColor.GREEN + "Price: $" + Utils.getTierItemPrice(5));
        tierFiveMeta.setLore(fiveLore);
        tierFiveMeta.setDisplayName(Utils.getTierItemName(5));
        tierFive.setItemMeta(tierFiveMeta);

        ItemStack info = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = info.getItemMeta();
        meta.setDisplayName(targetWorld.getName());
        List<String> infoLore = new ArrayList<>();
        infoLore.add(ChatColor.GREEN + "RTP to world: " + targetWorld.getName());
        meta.setLore(infoLore);
        info.setItemMeta(meta);

        initItems(tierInv, background, tierOne, tierTwo, tierThree, tierFour, tierFive, info);
        return tierInv;
    }

    public void openTierGUI(Player player, World targetWorld){
        player.openInventory(createTierInventory(targetWorld));
    }


    private void initItems(Inventory inv, ItemStack background, ItemStack tierOne, ItemStack tierTwo, ItemStack tierThree, ItemStack tierFour, ItemStack tierFive, ItemStack info) {
        inv.setItem(0, background);
        inv.setItem(1, background);
        inv.setItem(2, background);
        inv.setItem(3, background);
        inv.setItem(4, info);
        inv.setItem(5, background);
        inv.setItem(6, background);
        inv.setItem(7, background);
        inv.setItem(8, background);
        inv.setItem(9, background);
        inv.setItem(10, background);

        inv.setItem(11, tierOneEnabled ? tierOne : background);
        inv.setItem(12, tierTwoEnabled ? tierTwo : background);
        inv.setItem(13, tierThreeEnabled ? tierThree : background);
        inv.setItem(14, tierFourEnabled ? tierFour : background);
        inv.setItem(15, tierFiveEnabled ? tierFive : background);

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
