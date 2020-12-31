package me.jakub.randomtp.commands;

import me.jakub.randomtp.Randomtp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class rtpsetbordercommand implements CommandExecutor {

    static Randomtp plugin;

    public rtpsetbordercommand(Randomtp main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length == 1){ //If player provided a number
                try { //Checks if the arg is a number
                    Integer num = Integer.parseInt(args[0]);
                    if (num <= 30000000 && num >= 10) { //Checks if it is lower than 30mil and higher than 10
                        plugin.getConfig().set("border", num);
                        player.sendMessage("§6Set border to §b" + args[0] + "§6 blocks"); //Sets the int "blocks" to the number in the config
                    }else{
                        Player player2 = (Player) sender;
                        player2.sendMessage("§cThe number has to be lower than 30.000.000 and over 10 blocks!"); //Higher than 30mil or lower than 10
                    }
                }catch (Exception e){
                    Player player1 = (Player) sender;
                    player1.sendMessage("§cUsage: /rtpsetborder <10-30000000>"); //Arg is a string instead of an integer
                }

            }else{
                player.sendMessage("§cYou didn't enter a valid number!"); //Either too many or none args were provided
            }
        }else{
            System.out.println("Only a player can execute this command."); //Executed by console
        }
        return true;
    }
}
