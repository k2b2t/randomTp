package me.jakub.randomtp.listeners;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.TeleportUtils;
import me.jakub.randomtp.utils.Utils;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignEvents implements Listener {


    static Randomtp plugin;

    public SignEvents(Randomtp plugin) {
        this.plugin = plugin;
    }

    TeleportUtils teleportUtils = new TeleportUtils(plugin);

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player player = e.getPlayer();
        Sign sign = (Sign) e.getBlock().getState();
        if (Utils.getEnabledSigns()) {
            if (e.getLine(0).equalsIgnoreCase("[RandomTP]") || e.getLine(0).equalsIgnoreCase("[RTP]")) {
                if (player.hasPermission("randomTp.sign.create")) {
                    e.setCancelled(true);
                    sign.setLine(0, Utils.getSignLine(0));
                    sign.setLine(1, Utils.getSignLine(1));
                    sign.setLine(2, Utils.getSignLine(2));
                    sign.setLine(3, Utils.getSignLine(3));
                    sign.update();
                    player.sendMessage(Utils.getSignCreateMessage());
                } else {
                    e.setCancelled(true);
                    player.sendMessage(Utils.getNoPermission());
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Utils.getEnabledSigns()) {
                if (e.getClickedBlock().getState() instanceof Sign) {
                    Sign sign = (Sign) e.getClickedBlock().getState();

                    if (sign.getLine(0).equalsIgnoreCase(Utils.getSignLine(0)) &&
                            sign.getLine(1).equalsIgnoreCase(Utils.getSignLine(1)) &&
                            sign.getLine(2).equalsIgnoreCase(Utils.getSignLine(2)) &&
                            sign.getLine(3).equalsIgnoreCase(Utils.getSignLine(3))) {
                        Player player = e.getPlayer();
                        if (player.hasPermission("randomTp.sign.use")) {
                            teleportUtils.rtpPlayer(player, null, true, true, false, null, false);
                        } else {
                            player.sendMessage(Utils.getNoPermission());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (Utils.getEnabledSigns()) {
            if (e.getBlock().getState() instanceof Sign) {
                Sign sign = (Sign) e.getBlock().getState();
                if (sign.getLine(0).equalsIgnoreCase(Utils.getSignLine(0)) &&
                        sign.getLine(1).equalsIgnoreCase(Utils.getSignLine(1)) &&
                        sign.getLine(2).equalsIgnoreCase(Utils.getSignLine(2)) &&
                        sign.getLine(3).equalsIgnoreCase(Utils.getSignLine(3))) {
                    Player player = e.getPlayer();
                    if (player.hasPermission("randomTp.sign.break")) {
                        if (player.isSneaking()) {
                            player.sendMessage(Utils.getSignRemoveMessage());
                        } else {
                            e.setCancelled(true);
                            player.sendMessage(Utils.getNotShiftMessage());
                        }
                    } else {
                        e.setCancelled(true);
                        player.sendMessage(Utils.getNoPermission());
                    }
                }
            }
        }
    }

    //finally
}
