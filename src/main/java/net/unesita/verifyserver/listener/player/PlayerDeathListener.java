package net.unesita.verifyserver.listener.player;

import net.unesita.verifyserver.VerifyServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final VerifyServer plugin;

    public PlayerDeathListener(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player target = event.getEntity().getKiller();
        if (target != null) {
            event.setDeathMessage(VerifyServer.PREFIX + "§e" + player.getName() + " §7wurde von §e" + target.getName() + " §7getötet!");
            target.setHealth(20);
        } else {
            event.setDeathMessage(null);
        }

        event.getDrops().clear();

        Bukkit.getScheduler().scheduleAsyncDelayedTask(this.plugin, () -> {
            player.teleport(new Location(Bukkit.getWorld("world"), -37.5, 186, -40.5, 0, 0));
            VerifyServer.getInstance().getPlayerInventory().setPlayerItems(player);
        }, 2L);

    }

}
