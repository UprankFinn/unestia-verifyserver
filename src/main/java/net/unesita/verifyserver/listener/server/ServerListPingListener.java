package net.unesita.verifyserver.listener.server;

import net.unesita.verifyserver.VerifyServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    private final VerifyServer plugin;

    public ServerListPingListener(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onServerListPingEvent(ServerListPingEvent event){
        event.setMotd("                    §8» §6§lUNESTIA.NET §8«");
        event.setMaxPlayers(100);
    }

}
