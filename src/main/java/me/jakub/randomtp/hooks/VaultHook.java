package me.jakub.randomtp.hooks;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class VaultHook {

        static Economy economy = Randomtp.getEconomy();


        public static boolean takeMoney(Player player, double amount){
                if(player.hasPermission("randomTp.price.bypass")){
                        return true;
                }
                if (hasMoney(player, amount)){
                        removeMoney(player, amount);
                        player.sendMessage(Utils.getTookMoneyMessage(amount));
                        return true;
                }else{
                        player.sendMessage(Utils.getNotEnoughMoneyMessage());
                        return false;
                }
        }


        private static boolean hasMoney(Player player, double money){
                return economy.has(player, money);
        }

        private static void removeMoney(Player player, double money){
                economy.withdrawPlayer(player, money);
        }

}
