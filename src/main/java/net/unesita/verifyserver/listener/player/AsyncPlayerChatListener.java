package net.unesita.verifyserver.listener.player;

import net.unesita.verifyserver.VerifyServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    private final VerifyServer plugin;

    public AsyncPlayerChatListener(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {

        String message = event.getMessage().replaceAll("%", "%%");
        event.setFormat(event.getPlayer().getDisplayName() + " §8» §f" + message);

    }

}
