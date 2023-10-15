package net.unesita.verifyserver.command;

import lombok.SneakyThrows;
import net.unesita.verifyserver.VerifyServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VerifyCommand implements CommandExecutor {

    private final VerifyServer plugin;

    public VerifyCommand(VerifyServer plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("verify").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;

        if (args.length == 0) {
            this.plugin.getPlayerEntityHandler().verifyPlayer(player);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("delete")) {
                this.plugin.getPlayerEntityHandler().unVerifyPlayer(player);
            } else {
                player.sendMessage(VerifyServer.PREFIX + "§7Usage: §9/verify");
                player.sendMessage(VerifyServer.PREFIX + "§7Usage: §9/verify delete");
            }
        } else {
            player.sendMessage(VerifyServer.PREFIX + "§7Usage: §9/verify");
            player.sendMessage(VerifyServer.PREFIX + "§7Usage: §9/verify delete");
        }
        return false;
    }
}
