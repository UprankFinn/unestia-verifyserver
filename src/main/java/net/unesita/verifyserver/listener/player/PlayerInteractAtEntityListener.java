package net.unesita.verifyserver.listener.player;

import net.unesita.verifyserver.VerifyServer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtEntityListener implements Listener {

    private final VerifyServer plugin;

    public PlayerInteractAtEntityListener(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getEntityId() == 1) {
            event.getPlayer().openInventory(this.plugin.getVerifyInventory().getInventory());
        } else if (event.getRightClicked().getEntityId() == 2) {
            event.getPlayer().sendMessage(VerifyServer.PREFIX + "§cDiese Funktion folgt in Kürze!");
        }
    }

}
