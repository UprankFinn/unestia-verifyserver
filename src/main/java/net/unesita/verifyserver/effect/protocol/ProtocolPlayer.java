package net.unesita.verifyserver.effect.protocol;

import com.mojang.authlib.GameProfile;
import lombok.Getter;
import net.unesita.verifyserver.VerifyServer;
import net.unesita.verifyserver.effect.protocol.packet.ProtocolPacket;
import net.unesita.verifyserver.effect.protocol.packet.ProtocolPacketPlayOutChat;
import net.unesita.verifyserver.effect.protocol.util.ProtocolChatComponentText;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProtocolPlayer {

    @Getter
    private ProtocolAPI protocolAPI;
    @Getter
    private Player player;

    public ProtocolPlayer(Player player) {
        this.player = player;
        this.protocolAPI = ProtocolManager.getInstance();
    }

    public void sendPacket(ProtocolPacket packet) {
        ProtocolManager.sendPacket(packet, player);
    }

    public void sendPacket(Object packet) {
        ProtocolManager.sendPacket(packet, player);
    }

    public void sendAsyncMessage(String message) {
        Bukkit.getScheduler().runTaskAsynchronously(VerifyServer.getInstance(), () -> {
            ProtocolPacketPlayOutChat chat = new ProtocolPacketPlayOutChat(new ProtocolChatComponentText(message), (byte) 0);
            sendPacket(chat);
        });
    }

    public Object getWorld() {
        try {
            Method profile = protocolAPI.getMinecraftServerInstance("EntityPlayer").getMethod("getWorld", new Class[0]);
            return profile.invoke(NMS(), new Object[0]);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.getStackTrace();
            return null;
        }
    }

    public GameProfile getProfile() {
        try {
            Method profile = protocolAPI.getMinecraftServerInstance("EntityPlayer").getMethod("getProfile", new Class[0]);
            return (GameProfile) profile.invoke(NMS(), new Object[0]);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.getStackTrace();
            return new GameProfile(player.getUniqueId(), player.getName());
        }
    }

    public Object NMS() {
        try {
            Class<?> typeCraftPlayer = protocolAPI.getCraftInstance("entity.CraftPlayer");
            Method getHandle = typeCraftPlayer.getMethod("getHandle", new Class[0]);
            return getHandle.invoke(player, new Object[0]);
        } catch (Exception ex) {
            return null;
        }
    }

}
