package net.unesita.verifyserver.listener.player;

import net.unesita.verifyserver.VerifyServer;
import net.unestia.api.UnestiaAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;

public class PlayerLoginListener implements Listener {

    private final VerifyServer plugin;

    public PlayerLoginListener(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLogin(PlayerLoginEvent playerLoginEvent) {
        Player player = playerLoginEvent.getPlayer();
        if (UnestiaAPI.getInstance().getPlayerManager().getPlayer(player.getUniqueId()) == null) {
            UnestiaAPI.getInstance().getPlayerManager().createPlayer(player.getUniqueId(), player.getName(), 0, 1, 1, "de_DE", new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>(), null, null);
        }
    }
}
