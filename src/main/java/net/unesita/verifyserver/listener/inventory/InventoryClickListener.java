package net.unesita.verifyserver.listener.inventory;

import net.unesita.verifyserver.VerifyServer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private final VerifyServer plugin;

    public InventoryClickListener(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory().getName().equals("§6Verifizieren")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§8» §eTeamSpeak")) {
                player.closeInventory();
                this.plugin.getPlayerEntityHandler().verifyPlayer(player);
            }
        }

    }

}
