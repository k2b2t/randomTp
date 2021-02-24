package me.jakub.randomtp.commands;

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
            }
        }

        return null;
    }
}
