package me.jakub.randomtp.command.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RTPluginCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            List<String> strings = new ArrayList<>();
            Player p = (Player) commandSender;

            if (args.length == 1) {
                strings.add("help");
                strings.add("setborder");
                strings.add("reload");

                return strings;
            }else if (args.length == 2 && args[0].equalsIgnoreCase("help")){
                List<String> strings1 = new ArrayList<>();
                strings1.add("commands");
                strings1.add("permissions");

                return strings1;
            }
        }

        return null;
    }
}
