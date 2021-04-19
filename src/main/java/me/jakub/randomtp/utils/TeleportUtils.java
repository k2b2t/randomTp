package me.jakub.randomtp.utils;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.command.commands.CommandRTP;
import me.jakub.randomtp.gui.ConfirmGUI;
import me.jakub.randomtp.gui.TierGUI;
import me.jakub.randomtp.gui.WorldGUI;
import me.jakub.randomtp.hooks.ClaimHookManager;
import me.jakub.randomtp.hooks.VaultHook;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Locale;
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


    static int count;
    public static HashSet<Player> hasCountdown = new HashSet<Player>();
    public static HashSet<Player> willTp = new HashSet<Player>();


    public Location generateLocation(Player player, World world, Utils.RTPTier tier) {
        //Called upon when generating a location
        Random random = new Random();

        boolean forceWorld = Utils.getForceDefaultWorldEnabled();

        int x = 0;
        int y = 0;
        int z = 0;

        int var1 = random.nextInt(Utils.getBorderForWorld(world.getName(), tier)); //X coordinate
        int var2 = random.nextInt(Utils.getBorderForWorld(world.getName(), tier)); //Z coordinate


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

        Location randomLocation;
        if (!forceWorld) {
            randomLocation = new Location(world, x, y, z); //create a new location
        } else {
            randomLocation = new Location(world, x, y, z); //create a new location
        }

        switch (randomLocation.getWorld().getEnvironment()) {
            case NORMAL:
                setYOver(randomLocation);
                break;
            case NETHER:
                setYNether(randomLocation);
                break;
            case THE_END:
                setYOver(randomLocation);
                break;
            default:
                setYOver(randomLocation);
                break;
        }
        return randomLocation;
        //Note: don't return null here
    }

    public void setYOver(Location randomLocation) {
        int y = randomLocation.getWorld().getHighestBlockYAt(randomLocation); //set the Y coordinate to the highest point

        randomLocation.setY(y + 1);
    }

    public void setYNether(Location randomLocation) {
        int x = randomLocation.getBlockX();
        int z = randomLocation.getBlockZ();
        int y = 126;
        for (int i = 0; i < randomLocation.getWorld().getMaxHeight(); i++) {
            Block cblock = randomLocation.getWorld().getBlockAt(x, i, z);
            Block ublock = randomLocation.getWorld().getBlockAt(x, i - 1, z);
            Block ablock = randomLocation.getWorld().getBlockAt(x, i + 1, z);
            if (!ablock.getType().isSolid() &&
                    !bad_blocks.contains(ablock.getType()) &&
                    cblock.getType().isSolid() &&
                    !bad_blocks.contains(cblock) &&
                    !ublock.getType().isSolid() &&
                    !(cblock.getY() >= 126) //Check if the Y isn't above the nether roof
            ) {
                y = i;
                randomLocation.setY(y + 1);
                return;
            }
        }
        randomLocation.setY(y + 1);
    }


    /**
     * Generates a safe location
     *
     * @param player Player to get the world from
     * @return Returns the location if successful, returns null if it couldn't generate a location
     */
    public Location startGenerateLocation(Player player, Biome biome, World world, Utils.RTPTier tier) {
        int maxAttempts = Utils.getMaxAttempts();
        int attempts = 0;
        while (attempts < maxAttempts) {
            Location loc = generateLocation(player, world, tier);
            if (!isLocationSafe(loc, biome)) {
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
    public boolean checkGeneratedLocation(Location loc, Biome biome) {
        if (loc != null && isLocationSafe(loc, biome)) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isLocationSafe(Location location, Biome biome) {
        //Checking if the generated random location is safe
        ClaimHookManager claimHookManager = new ClaimHookManager(plugin);

        if (claimHookManager.isClaimedAt(location)) return false;

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

        if (biome != null) {
            if (location.getBlock().getBiome() != biome) {
                return false;
            }
        }

        if (location.getWorld().getEnvironment() == World.Environment.NORMAL) {
            return !(bad_blocks.contains(above.getType()))
                    && !(bad_blocks.contains(block.getType()))
                    && !(bad_blocks.contains(below.getType()))
                    && !(above.getType().isSolid())
                    && !(block.getType().isSolid())
                    && below.getType().isSolid();
        } else if (location.getWorld().getEnvironment() == World.Environment.NETHER) {
            return (
                    !bad_blocks.contains(below.getType()) &&
                            !block.getType().isSolid() &&
                            !above.getType().isSolid() &&
                            below.getType().isSolid() &&
                            !(location.getBlockY() >= 126)
            );
        } else if (location.getWorld().getEnvironment() == World.Environment.THE_END) {
            return !(bad_blocks.contains(above.getType()))
                    && !(bad_blocks.contains(block.getType()))
                    && !(bad_blocks.contains(below.getType()))
                    && !(above.getType().isSolid())
                    && !(block.getType().isSolid())
                    && below.getType().isSolid();
        } else {
            return false;
        }

    }

    public void startTp(Player player, Location location, boolean bypassCountdown, boolean bypassPrice, boolean startCooldown, Biome biome, World world, Utils.RTPTier tier) {

        boolean countdownEnabled = plugin.getConfig().getBoolean("Countdown.enabled");

        if (!checkGeneratedLocation(location, biome)) {
            player.sendMessage(Utils.getCouldntGenerateMessage());
            return;
        }

        if (Utils.isWorldDisabled(location.getWorld().getName())) {
            player.sendMessage(Utils.getWorldDisabledMessage());
            return;
        }


        if (!player.hasPermission("randomTp.countdown.bypass") && countdownEnabled) {
            if (bypassCountdown) {
                tp(player, location, bypassPrice, tier);
            } else {
                count = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                    int baseCount = Utils.getCountdown();
                    int startCount = baseCount;

                    @Override
                    public void run() {
                        if (startCount == baseCount) {
                            hasCountdown.add(player);
                            willTp.add(player);
                            player.sendMessage(Utils.getCountdownMessage(baseCount));
                        } else if (startCount < baseCount && startCount > 0) {
                            if (Utils.getCountdownMessageEnabled() && willTp.contains(player)) {
                                player.sendMessage(Utils.getCountingDownMessage(startCount));
                            }
                        }
                        startCount--;
                        if (startCount == -1) {
                            Bukkit.getScheduler().cancelTask(count);
                            startCount = baseCount;
                            hasCountdown.remove(player);
                            if (willTp.contains(player)) {
                                tp(player, location, bypassPrice, tier);
                                if (startCooldown) {
                                    CommandRTP.cooldowns.put(player.getName(), System.currentTimeMillis() + (plugin.getConfig().getInt("Cooldown.seconds") * 1000));
                                }
                            }
                            willTp.remove(player);
                        }
                    }
                }, 0, 20);
            }
        } else {
            //Has countdown bypass perm or countdown is disabled
            tp(player, location, bypassPrice, tier);
        }

    }

    public void tp(Player player, Location location, boolean bypassPrice, Utils.RTPTier tier) {

        if (Randomtp.vaultHooked && !bypassPrice) {
            if (!VaultHook.takeMoney(player, Utils.getAmount(), tier)) {
                return;
            }
        }
        location.add(0.5, 0, 0.5); //Set the location to the center of the block
        location.getWorld().getChunkAt(location.getBlock()).load(true);
        location.setPitch(player.getLocation().getPitch());
        location.setYaw(player.getLocation().getYaw());
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
     * @param target          Player to RTP
     * @param sender          Command sender (nullable)
     * @param bypassCountdown Bypass countdown
     * @param bypassPrice     Bypass RTP price
     * @param startCooldown   Should a cooldown start for the player target
     * @param biomeString     Name of a biome (null for no specific biome)
     * @param biomeOutput     Should it output biome messages
     * @param guiChecked      True if you don't want to use the confirm GUI
     * @param worldChecked    False to show the world GUI
     * @param world           World to RTP to (overrides everything else)
     * @param tierGUIChecked  True if you don't want the Tier GUI to appear
     * @param forceTier       Tier that will override the border
     */
    public void rtpPlayer(Player target, Player sender, boolean bypassCountdown, boolean bypassPrice, boolean startCooldown, String biomeString, boolean biomeOutput, boolean guiChecked, boolean worldChecked, World world, boolean tierGUIChecked, Utils.RTPTier forceTier) {
        if (!guiChecked && Utils.isConfirmGUIEnabled() && !Utils.getWorldGUIEnabled() && !Utils.getTierGUIEnabled()) {
            ConfirmGUI confirmGUI = new ConfirmGUI();
            confirmGUI.openConfirmGUI(target);
            return;
        }

        if (!worldChecked && Utils.getWorldGUIEnabled()) {
            WorldGUI worldGUI = new WorldGUI();
            worldGUI.openWorldGUI(target, !tierGUIChecked);
            return;
        }

        if (world == null) {
            world = Utils.getForceDefaultWorldEnabled() ? Utils.forcedWorld(target) : target.getWorld();
        }

        if (!tierGUIChecked && forceTier == null && !Utils.getWorldGUIEnabled() && Utils.getTierGUIEnabled()) {
            TierGUI tierGUI = new TierGUI();
            tierGUI.openTierGUI(target, world);
            return;
        }

        Biome biome = null;
        if (Utils.isWorldDisabled(world.getName())) {
            target.sendMessage(Utils.getWorldDisabledMessage());
            return;
        }
        if (biomeString != null) {
            try {
                biome = Biome.valueOf(biomeString.toUpperCase(Locale.ROOT));
            } catch (Exception e) {
                if (biomeOutput && sender != null) {
                    sender.sendMessage(Utils.getWrongBiomeMessage());
                }
                return;
            }
            biome = Biome.valueOf(biomeString.toUpperCase(Locale.ROOT));
            Location loc = startGenerateLocation(target, biome, world, forceTier);
            startTp(target, loc, bypassCountdown, bypassPrice, startCooldown, biome, world, forceTier);
            if (biomeOutput && sender != null) {
                sender.sendMessage(Utils.getTpMessageSenderBiome(target, biome));
            }
        } else {
            Location loc = startGenerateLocation(target, biome, world, forceTier);
            startTp(target, loc, bypassCountdown, bypassPrice, startCooldown, biome, world, forceTier);
        }
    }

    public String testRTP(Player player) {
        long timestampStart = System.currentTimeMillis();
        Location loc = startGenerateLocation(player, null, player.getWorld(), null);
        long timestampEnd = System.currentTimeMillis();
        long duration = timestampEnd - timestampStart;
        StringBuilder sb = new StringBuilder();
        sb.append("§6Location generated in §c" + duration + "ms" + "\n")
                .append("§6X: §c" + loc.getBlockX() + "\n")
                .append("§6Y: §c" + loc.getBlockY() + "\n")
                .append("§6Z: §c" + loc.getBlockZ());
        return sb.toString();
    }

    public String testRTP() {
        long timestampStart = System.currentTimeMillis();
        Location loc = startGenerateLocation(null, null, Bukkit.getWorlds().get(0), null);
        long timestampEnd = System.currentTimeMillis();
        long duration = timestampEnd - timestampStart;
        StringBuilder sb = new StringBuilder();
        sb.append("§6Location generated in §c" + duration + "ms" + "\n")
                .append("§6X: §c" + loc.getBlockX() + "\n")
                .append("§6Y: §c" + loc.getBlockY() + "\n")
                .append("§6Z: §c" + loc.getBlockZ());
        return sb.toString();
    }

}
