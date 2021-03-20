package me.jakub.randomtp.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RTPCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            List<String> strings = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                strings.add(players[i].getName());
            }
            strings.add("@everyone");

            if (args.length == 1) {
                return strings;
            } else if (args.length == 2) {
                List<String> worlds = new ArrayList<>();
                for (World world : Bukkit.getWorlds()) {
                    worlds.add(world.getName());
                }
                return worlds;
            } else if (args.length == 3) {
                List<String> biomes = new ArrayList<>();
                for (Biome biome : Biome.values()) {
                    biomes.add(biome.name().toLowerCase(Locale.ROOT));
                }

                return biomes;
            }

        }
        return null;
    }
}
