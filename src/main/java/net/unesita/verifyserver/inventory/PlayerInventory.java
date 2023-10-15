package net.unesita.verifyserver.inventory;

import lombok.Getter;
import net.unesita.verifyserver.VerifyServer;
import net.unesita.verifyserver.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerInventory {

    @Getter
    private VerifyServer plugin;

    @Getter
    private final Inventory inventory;

    public PlayerInventory(VerifyServer plugin) {
        this.plugin = plugin;
        this.inventory = Bukkit.createInventory(null, 36);
        initializeInventory();
    }

    public void setPlayerItems(Player player) {
        player.getInventory().setContents(getInventory().getContents());
    }

    private void initializeInventory() {
        this.inventory.setItem(4, new ItemBuilder(Material.PAPER, 1, (byte) 0).setDisplayName("§6Verifizieren §7(Rechtsklick)").build());

        this.inventory.setItem(8, new ItemBuilder(Material.IRON_SWORD, 1, (byte) 0).setDisplayName("§6Schwert §7(Linksklick)").setUnbreakable().build());
    }

}
