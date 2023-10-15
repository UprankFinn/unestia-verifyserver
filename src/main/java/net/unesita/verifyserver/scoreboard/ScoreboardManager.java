package net.unesita.verifyserver.scoreboard;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.unesita.verifyserver.VerifyServer;
import net.unestia.api.UnestiaAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;

public class ScoreboardManager {

    private final VerifyServer plugin;

    public ScoreboardManager(VerifyServer plugin) {
        this.plugin = plugin;
    }

    public Team getTeam(Scoreboard scoreboard, Player player) {
        if (player.isOp()) {
            return scoreboard.getTeam("a");
        } else {
            return scoreboard.getTeam("b");
        }
    }

    public void setScoreboard(Player player) {
        this.setPlayerTabList(player,
                "\n§8» §6§lUNESTIA.NET §8«\n§7Du spielst auf §eVerify\n",
                "\n§7Website §8» §6unestia.net\n§7Store §8» §6store.unestia.net\n§7TeamSpeak §8» §6ts.unestia.net\n");

        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("aaa", "bbb");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(" §8» §6§lUNESTIA.NET §8« ");

        objective.getScore("§a").setScore(6);
        objective.getScore("§eTeamSpeak:").setScore(5);

        {
            Team team = scoreboard.registerNewTeam("x1");
            team.setPrefix("§8» §6");
            if (UnestiaAPI.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getTeamSpeakId() != null) {
                team.setSuffix(ChatColor.WHITE + "Verifiziert");
            } else {
                team.setSuffix(ChatColor.WHITE + "Unverifiziert");
            }
            team.addEntry("§b");
            objective.getScore("§b").setScore(4);
        }

        objective.getScore("§c").setScore(3);

        objective.getScore("§eDiscord:").setScore(2);

        {
            Team team = scoreboard.registerNewTeam("x0");
            team.setPrefix("§8» §6");
            team.setSuffix(ChatColor.WHITE + "Unverifiziert");
            team.addEntry("§f");
            objective.getScore("§f").setScore(1);
        }

        objective.getScore("§r").setScore(0);

        player.setScoreboard(scoreboard);

    }

    public void updateScoreboard(Player player) {
        if (player.getScoreboard() == null || player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) return;

        if (UnestiaAPI.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getTeamSpeakId() != null) {
            player.getScoreboard().getTeam("x1").setSuffix(ChatColor.WHITE + "Verifiziert");
        } else {
            player.getScoreboard().getTeam("x1").setSuffix(ChatColor.WHITE + "Unverifiziert");
        }
        player.getScoreboard().getTeam("x0").setSuffix(ChatColor.WHITE + "Unverifiziert");

    }

    public void setPlayerTabList(Player player, String header, String footer) {
        if (header == null) {
            header = "";
        }
        if (footer == null) {
            footer = "";
        }
        IChatBaseComponent tabHeader = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + header + "\"}");
        IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field field1 = headerPacket.getClass().getDeclaredField("a");
            field1.setAccessible(true);
            field1.set(headerPacket, tabHeader);
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, tabFooter);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(headerPacket);
    }

}
