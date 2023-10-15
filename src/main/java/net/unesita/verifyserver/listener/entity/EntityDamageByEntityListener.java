package net.unesita.verifyserver.listener.entity;

import net.unesita.verifyserver.VerifyServer;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDamageByEntityListener implements Listener {

    private final VerifyServer plugin;

    public EntityDamageByEntityListener(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player target = (Player) event.getDamager();

            if (!player.canSee(target) || !target.canSee(player)) {
                event.setCancelled(true);
                return;
            }

            if (player.getInventory().getHeldItemSlot() == 8 && target.getInventory().getHeldItemSlot() == 8) {

            } else event.setCancelled(true);

        } else
            event.setCancelled(true);

    }

}
