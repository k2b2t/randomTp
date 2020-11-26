package me.jakub.randomtp;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Random;

public class TeleportUtils {

    static Randomtp plugin;

    public TeleportUtils(Randomtp plugin) {
        this.plugin = plugin;
    }

    public static HashSet<Material> bad_blocks = new HashSet<>();

    static{
        bad_blocks.add(Material.LAVA);
        bad_blocks.add(Material.FIRE);
        bad_blocks.add(Material.CACTUS);
        bad_blocks.add(Material.WATER);

    }



    public static Location generateLocation(Player player){
        //Called upon when /rtp is executed with the target "player"
        Random random = new Random();

        int x = 0;
        int y = 0;
        int z = 0;
        if(plugin.getConfig().getBoolean("world-border")){
             x = random.nextInt(plugin.getConfig().getInt("border"));
             y = 150;
             z = random.nextInt(plugin.getConfig().getInt("border"));
        }else if(!plugin.getConfig().getBoolean("world-border")){
            x = random.nextInt(1000);
            y = 150;
            z = random.nextInt(1000);
        }

        Location randomLocation = new Location(player.getWorld(), x, y,z);

        y = randomLocation.getWorld().getHighestBlockYAt(randomLocation);
        randomLocation.setY(y + 1);

        while(isLocationSafe(randomLocation) == false){
            randomLocation = generateLocation(player);
        }
        return randomLocation;
    }

    public static boolean isLocationSafe(Location location){
        //Checking if generated random location is safe
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Block block = location.getWorld().getBlockAt(x, y, z);
        Block below = location.getWorld().getBlockAt(x, y - 1, z);
        Block above = location.getWorld().getBlockAt(x, y + 1, z);

        return !(bad_blocks.contains(below.getType())) || (block.getType().isSolid()) || (above.getType().isSolid());
    }

}
