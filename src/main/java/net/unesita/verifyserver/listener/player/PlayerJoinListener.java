package net.unesita.verifyserver.listener.player;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.unesita.verifyserver.VerifyServer;
import net.unestia.api.UnestiaAPI;
import net.unestia.api.player.Player;
import net.unestia.api.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final VerifyServer plugin;

    public PlayerJoinListener(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        this.plugin.getPlayerInventory().setPlayerItems(event.getPlayer());

        this.plugin.getScoreboardManager().setScoreboard(event.getPlayer());

        event.getPlayer().setGameMode(GameMode.SURVIVAL);


        Player playerObject = UnestiaAPI.getInstance().getPlayerManager().getPlayer(event.getPlayer().getUniqueId());
        Rank rankObject = playerObject.getRank();

        this.plugin.getScoreboard().getTeam(rankObject.getSortId() + rankObject.getName()).getPlayerNameSet().add(event.getPlayer().getName());
        if (rankObject.getPrefix() != null) {
            event.getPlayer().setDisplayName(rankObject.getColor() + rankObject.getPrefix() + " §8| " + rankObject.getColor() + event.getPlayer().getName());
        } else {
            event.getPlayer().setDisplayName(rankObject.getColor() + event.getPlayer().getName());
        }

        this.plugin.getServer().getOnlinePlayers().forEach(players -> {
            this.plugin.getScoreboard().getTeams().forEach(scoreboardTeams -> {
                ((CraftPlayer) players).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(scoreboardTeams, 1));
                ((CraftPlayer) players).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(scoreboardTeams, 0));
            });
        });

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this.plugin, () -> VerifyServer.getInstance().getScoreboardManager().updateScoreboard(event.getPlayer()), 20L, 20L);

        event.setJoinMessage(null);

    }

}
