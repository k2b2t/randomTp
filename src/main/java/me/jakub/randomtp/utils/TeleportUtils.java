package me.jakub.randomtp.utils;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.commands.RTPCommand;
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

    static {
        bad_blocks.add(Material.LAVA);
        bad_blocks.add(Material.FIRE);
        bad_blocks.add(Material.CACTUS);
        bad_blocks.add(Material.WATER);
        bad_blocks.add(Material.MAGMA_BLOCK);
    }


    static int startCount = 5;
    static int count;
    public static HashSet<Player> hasCountdown = new HashSet<Player>();
    public static HashSet<Player> willTp = new HashSet<Player>();


    public Location generateLocation(Player player) {
        //Called upon when generating a location
        Random random = new Random();

        int x = 0;
        int y = 0;
        int z = 0;

        int border = plugin.getConfig().getInt("border");
        int var1 = random.nextInt(border); //X coordinate
        int var2 = random.nextInt(border); //Z coordinate

        if (Utils.isWorldSet(player)) {
            var1 = random.nextInt(Utils.getBorderForWorld(player.getWorld().getName()));
            var2 = random.nextInt(Utils.getBorderForWorld(player.getWorld().getName()));
        }


        int var3 = random.nextInt(2); //basically a random boolean
        if (var3 == 1) {
            var1 = var1 * -1; //50% chance the x coordinate will be negative

        }
        var3 = random.nextInt(2);
        if (var3 == 1) {
            var2 = var2 * -1; //50% chance the x coordinate will be negative
        }

        x = var1;
        y = 150; //useless line of code :)
        z = var2;


        Location randomLocation = new Location(player.getWorld(), x, y, z); //create a new location

        switch (player.getWorld().getEnvironment()) {
            case NORMAL:
                setYOver(randomLocation);
                break;
            case NETHER://TODO nether rtp
                break;
        }

        return randomLocation;
    }

    public void setYOver(Location randomLocation) {
        int y = randomLocation.getWorld().getHighestBlockYAt(randomLocation); //set the Y coordinate to the highest point
        randomLocation.setY(y + 1);
    }


    /**
     * Generates a safe location
     *
     * @param player Player to get the world from
     * @return Returns the locations if successful, returns null if it couldn't generate a location
     */
    public Location startGenerateLocation(Player player) {
        int maxAttempts = Utils.getMaxAttempts();
        int attempts = 0;
        while (attempts < maxAttempts) {
            Location loc = generateLocation(player);
            if (!isLocationSafe(loc)) {
                attempts++;
            } else {
                attempts = 0;
                return loc;
            }
        }
        attempts = 0;
        return null;
    }


    /**
     * Checks if a location isn't null and is safe
     *
     * @param loc Location to check
     * @return Returns true if location is safe, otherwise returns false
     */
    public boolean checkGeneratedLocation(Location loc) {
        if (loc != null && isLocationSafe(loc)) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isLocationSafe(Location location) {
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

        return !(bad_blocks.contains(below.getType()))
                || (block.getType().isSolid())
                || (above.getType().isSolid());

    }

    public void startTp(Player player, Location location, boolean bypassCountdown, boolean bypassPrice, boolean startCooldown) {

        boolean countdownEnabled = plugin.getConfig().getBoolean("Countdown.enabled");

        if (!checkGeneratedLocation(location)) {
            player.sendMessage(Utils.getCouldntGenerateMessage());
            return;
        }

        if (!player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            player.sendMessage(Utils.getPlayerNotInOverMessage());
            return;
        }

        if (Utils.isWorldDisabled(location.getWorld().getName())) {
            player.sendMessage(Utils.getWorldDisabledMessage());
            return;
        }


        if (!player.hasPermission("randomTp.countdown.bypass") && countdownEnabled) {
            if (bypassCountdown) {
                tp(player, location, bypassPrice);
            } else {
                count = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (startCount == 5) {
                            hasCountdown.add(player);
                            willTp.add(player);
                            player.sendMessage(Utils.getCountdownMessage());
                        } else if (startCount < 5 && startCount > 0) {
                            if (Utils.getCountdownMessageEnabled() && willTp.contains(player)) {
                                player.sendMessage(Utils.getCountingDownMessage(startCount));
                            }
                        }
                        startCount--;
                        if (startCount == -1) {
                            Bukkit.getScheduler().cancelTask(count);
                            startCount = 5;
                            hasCountdown.remove(player);
                            if (willTp.contains(player)) {
                                tp(player, location, bypassPrice);
                                if (startCooldown) {
                                    RTPCommand.cooldowns.put(player.getName(), System.currentTimeMillis() + (plugin.getConfig().getInt("Cooldown.seconds") * 1000));
                                }
                            }
                            willTp.remove(player);
                        }
                    }
                }, 0, 20);
            }
        } else {
            //Has countdown bypass perm or countdown is disabled
            tp(player, location, bypassPrice);
        }

    }

    public void tp(Player player, Location location, boolean bypassPrice) {

        if (Randomtp.vaultHooked && !bypassPrice) {
            if (!VaultHook.takeMoney(player, Utils.getAmount())) {
                return;
            }
        }
        location.getWorld().getChunkAt(location.getBlock()).load(true);
        player.teleport(location);
        afterTp(player);

    }


    public void afterTp(Player player) {
        if (plugin.getConfig().getBoolean("Titles.enabled")) {
            player.sendTitle(
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Titles.generating-title")),
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Titles.generating-subtitle")),
                    0,
                    50,
                    20
            );
        }

        if (plugin.getConfig().getBoolean("Sounds.enabled")) {
            PlayerUtils.playSound(player);
        }
        if (plugin.getConfig().getBoolean("Invincibility.enabled")) {
            PlayerUtils.addInvincibility(player, plugin.getConfig().getInt("Invincibility.potion-seconds"), plugin.getConfig().getInt("Invincibility.potion-amplifier"));
        }
        if (plugin.getConfig().getBoolean("Particles.enabled")) {
            PlayerUtils.spawnParticle(player);
        }
        player.sendMessage(Utils.getTpMessage());
    }

    /**
     * RTP a player
     *
     * @param player          Player to RTP
     * @param bypassCountdown Bypass the countdown
     * @param bypassPrice     Bypass the price
     */
    public void rtpPlayer(Player player, boolean bypassCountdown, boolean bypassPrice, boolean startCooldown) {
        Location loc = startGenerateLocation(player);
        startTp(player, loc, bypassCountdown, bypassPrice, startCooldown);
    }


}
