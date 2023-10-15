package net.unesita.verifyserver.listener.player;

import net.unesita.verifyserver.VerifyServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * @author: Uprank
 * @date: 25.12.2020
 */

public class PlayerCommandPreprocessListener implements Listener {

    private final VerifyServer plugin;

    public PlayerCommandPreprocessListener(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        String[] command = event.getMessage().replaceAll("&", "§").split(" ");
        if (command[0].equalsIgnoreCase("/plugins") || command[0].equalsIgnoreCase("/pl") || command[0].equalsIgnoreCase("/me") || command[0].equalsIgnoreCase("/say") || command[0].equalsIgnoreCase("/tell") || command[0].equalsIgnoreCase("/msg")
                || command[0].equalsIgnoreCase("/icanhasbukkit") || command[0].equalsIgnoreCase("/version") || command[0].equalsIgnoreCase("/about") || command[0].equalsIgnoreCase("/bukkit") || command[0].equalsIgnoreCase("/ver") || command[0].equalsIgnoreCase("/help")) {
            event.setCancelled(true);

            player.sendMessage(VerifyServer.PREFIX + "§cDafür hast du keine Rechte!");

        }
    }
}
