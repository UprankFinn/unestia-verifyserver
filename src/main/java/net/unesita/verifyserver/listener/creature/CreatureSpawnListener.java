package net.unesita.verifyserver.listener.creature;

import net.unesita.verifyserver.VerifyServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * @author: Uprank
 * @date: 03.01.2021
 */

public class CreatureSpawnListener implements Listener {

    private final VerifyServer plugin;

    public CreatureSpawnListener(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }

}
