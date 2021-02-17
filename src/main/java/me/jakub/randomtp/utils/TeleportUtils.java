package me.jakub.randomtp.utils;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.hooks.VaultHook;
import org.bukkit.*;
import org.bukkit.block.Biome;
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

    static int startCount = 5;
    static int count;
    public static HashSet<Player> hasCountdown = new HashSet<Player>();
    public static HashSet<Player> willTp = new HashSet<Player>();


    public static Location generateLocation(Player player){
        //Called upon when generating a location
        Random random = new Random();

        int x = 0;
        int y = 0;
        int z = 0;

        int border = plugin.getConfig().getInt("border");
        int var1 = random.nextInt(border); //X coordinate
        int var2 = random.nextInt(border); //Z coordinate

        if (Utils.isWorldSet(player)){
            var1 = random.nextInt(Utils.getBorderForWorld(player.getWorld().getName()));
            var2 = random.nextInt(Utils.getBorderForWorld(player.getWorld().getName()));
        }



        int var3 = random.nextInt(2); //basically a random boolean
        if (var3 == 1){
            var1 = var1 * -1; //50% chance the x coordinate will be negative

        }
        var3 = random.nextInt(2);
        if(var3 == 1){
            var2 = var2 * -1; //50% chance the x coordinate will be negative
        }

        x = var1;
        y = 150; //useless line of code :)
        z = var2;


        Location randomLocation = new Location(player.getWorld(), x, y,z); //create a new location

        y = randomLocation.getWorld().getHighestBlockYAt(randomLocation); //set the Y coordinate to the highest point
        randomLocation.setY(y + 1);

            while (isLocationSafe(randomLocation) == false) {
                randomLocation = generateLocation(player);
            }
        return randomLocation;
    }

    public static boolean isLocationSafe(Location location){
        //Checking if the generated random location is safe
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Block block = location.getWorld().getBlockAt(x, y, z);
        Block below = location.getWorld().getBlockAt(x, y - 1, z);
        Block above = location.getWorld().getBlockAt(x, y + 1, z);

        if (Utils.getBiomeBlacklistEnabled()) {
            for (String b : Utils.getBiomes()) {
                try {
                    if (Biome.valueOf(b).equals(location.getBlock().getBiome())) {
                        return false;
                    }
                } catch (Exception e) {
                    Log.log(Log.LogLevel.ERROR, "Wrong biome name was used in the config!");
                }
            }
        }

        return !(bad_blocks.contains(below.getType())) || (block.getType().isSolid()) || (above.getType().isSolid());
    }

    public static void afterTp(Player player, Location location, boolean bypassPrice){

        if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            if (Utils.isWorldDisabled(location.getWorld().getName())){
                player.sendMessage(Utils.getWorldDisabledMessage());
                return;
            }

        if(Randomtp.vaultHooked && !bypassPrice){
            if (!VaultHook.takeMoney(player, Utils.getAmount())) {
                return;
            }
        }


            if (plugin.getConfig().getBoolean("Titles.enabled")) {
                player.sendTitle(
                        ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Titles.generating-title")),
                        ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Titles.generating-subtitle")),
                        0,
                        50,
                        20
                );
            }
            location.getWorld().getChunkAt(location.getBlock()).load(true);
            player.teleport(location);
            if (plugin.getConfig().getBoolean("Sounds.enabled")){
                PlayerUtils.playSound(player);
            }
            if (plugin.getConfig().getBoolean("Invincibility.enabled")) {
                PlayerUtils.addInvincibility(player, plugin.getConfig().getInt("Invincibility.potion-seconds"), plugin.getConfig().getInt("Invincibility.potion-amplifier"));
            }
            if (plugin.getConfig().getBoolean("Particles.enabled")){
                PlayerUtils.spawnParticle(player);
            }
            player.sendMessage(Utils.getTpMessage());
        }else{
            player.sendMessage(Utils.getPlayerNotInOverMessage());
        }
    }

    public static void tp(Player player, Location location, boolean bypassCountdown, boolean bypassPrice){

        boolean countdownEnabled = plugin.getConfig().getBoolean("Countdown.enabled");


        if (!player.hasPermission("randomTp.countdown.bypass") && countdownEnabled) {
            if (bypassCountdown){
                afterTp(player, location, bypassPrice);
            }else {
                count = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (startCount == 5) {
                            hasCountdown.add(player);
                            willTp.add(player);
                            player.sendMessage("§6Teleporting you in " + startCount + " seconds.");
                            player.sendMessage("§4Do not move or take damage.");
                        }
                        startCount--;
                        if (startCount == 0) {
                            Bukkit.getScheduler().cancelTask(count);
                            startCount = 5;
                            hasCountdown.remove(player);
                            if (willTp.contains(player)) {
                                afterTp(player, location, bypassPrice);
                            }
                            willTp.remove(player);
                        }
                    }
                }, 0, 20);
            }
        }else{
            //Has countdown bypass perm or countdown is disabled
            afterTp(player, location, bypassPrice);
        }

    }

}
