package net.unesita.verifyserver.inventory;

import lombok.Getter;
import net.unesita.verifyserver.VerifyServer;
import net.unesita.verifyserver.utils.Base64;
import net.unesita.verifyserver.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class VerifyInventory {

    private final VerifyServer plugin;

    @Getter
    private final Inventory inventory = Bukkit.createInventory(null, 27, "§6Verifizieren");

    public VerifyInventory(VerifyServer plugin) {
        this.plugin = plugin;
        initializeInventory();
    }

    public void initializeInventory() {

        this.inventory.setItem(13, new ItemBuilder(Base64.getSkull("http://textures.minecraft.net/texture/f83f007e4304abd64075576d6a10aa7af139db94bee4c38628aa3a583f9842d2")).setDisplayName("§8» §eTeamSpeak").build());

    }

}
