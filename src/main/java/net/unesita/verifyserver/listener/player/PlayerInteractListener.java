package net.unesita.verifyserver.listener.player;

import net.unesita.verifyserver.VerifyServer;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    private final VerifyServer plugin;

    public PlayerInteractListener(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
        if (event.getItem() == null) return;
        if (!(event.getItem().hasItemMeta())) return;
        if (event.getItem().getItemMeta().getDisplayName() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;

        if ("§6Verifizieren §7(Rechtsklick)".equals(event.getItem().getItemMeta().getDisplayName())) {
            event.getPlayer().openInventory(this.plugin.getVerifyInventory().getInventory());
        }

    }

}
