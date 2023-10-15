package net.unesita.verifyserver.mongodb.entity;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import net.unesita.verifyserver.VerifyServer;
import net.unestia.api.UnestiaAPI;
import org.bukkit.entity.Player;

import java.net.InetAddress;

public class PlayerEntityHandler {

    private final VerifyServer plugin;

    public PlayerEntityHandler(VerifyServer plugin) {
        this.plugin = plugin;
    }

    public void verifyPlayer(Player player) {
        net.unestia.api.player.Player unestiaPlayer = UnestiaAPI.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
        if (unestiaPlayer.getTeamSpeakId() == null) {
            InetAddress inetAddress = player.getAddress().getAddress();

            Client client = this.plugin.getTeamSpeakBot().getTs3Api().getClients().stream().filter(clients -> clients.getIp().equals(inetAddress.getHostAddress())).findFirst().orElse(null);
            if (client != null) {
                if (!client.isInServerGroup(28)) {
                    unestiaPlayer.setTeamSpeakId(this.plugin.getTeamSpeakBot().getTs3Api().getClientInfo(client.getId()).getUniqueIdentifier());
                    this.plugin.getTeamSpeakBot().getTs3Api().addClientToServerGroup(28, client.getDatabaseId());
                    this.plugin.getTeamSpeakBot().getTs3Api().addClientToServerGroup(UnestiaAPI.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getRank().getTeamSpeakId(), client.getDatabaseId());
                    player.sendMessage(VerifyServer.PREFIX + "§aDu hast dich erfolgreich verifiziert!");
                } else {
                    player.sendMessage(VerifyServer.PREFIX + "§cDu bist bereits verifiziert!");
                }
            } else {
                player.sendMessage(VerifyServer.PREFIX + "§cBitte betrete dafür erst unseren TeamSpeak Server!");
            }
        } else {
            player.sendMessage(VerifyServer.PREFIX + "§cDu bist bereits verifiziert!");
        }
    }

    public void updateVerify(Player player) {
    }

    public void unVerifyPlayer(Player player) {
        net.unestia.api.player.Player unestiaPlayer = UnestiaAPI.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
        if (unestiaPlayer.getTeamSpeakId() != null) {
            try {
                InetAddress inetAddress = player.getAddress().getAddress();
                Client client = this.plugin.getTeamSpeakBot().getTs3Api().getClients().stream().filter(clients -> clients.getIp().equals(inetAddress.getHostAddress())).findFirst().orElse(null);
                if (client != null) {
                    if (client.isInServerGroup(28)) {
                        this.plugin.getTeamSpeakBot().getTs3Api().removeClientFromServerGroup(28, getDatabaseId(player));
                        this.plugin.getTeamSpeakBot().getTs3Api().removeClientFromServerGroup(UnestiaAPI.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getRank().getTeamSpeakId(), getDatabaseId(player));

                        unestiaPlayer.setTeamSpeakId(null);

                        player.sendMessage(VerifyServer.PREFIX + "§cDu hast dich erfolgreich unverifiziert!");
                    } else {
                        player.sendMessage(VerifyServer.PREFIX + "§cDu bist nicht in der verifiziert Gruppe, bitte melde dich im TeamSpeak Support!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            player.sendMessage(VerifyServer.PREFIX + "§cDu bist nicht verifiziert!");
        }

    }

    public Integer getDatabaseId(Player player) {
        String uniqueId = UnestiaAPI.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getTeamSpeakId();
        return this.plugin.getTeamSpeakBot().getTs3Api().getClients().stream().filter(client -> client.getUniqueIdentifier().equals(uniqueId)).findFirst().get().getDatabaseId();
    }
}
