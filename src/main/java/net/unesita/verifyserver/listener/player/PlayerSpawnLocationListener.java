package net.unesita.verifyserver.listener.player;

import net.unesita.verifyserver.VerifyServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerSpawnLocationListener implements Listener {

    private final VerifyServer plugin;

    public PlayerSpawnLocationListener(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerSpawnLocationEvent(PlayerSpawnLocationEvent event){
        event.setSpawnLocation(new Location(Bukkit.getWorld("world"), -37.5, 186, -40.5, 0, 0));
    }

}
