package net.unesita.verifyserver.effect.protocol.packet;

import lombok.Getter;
import net.unesita.verifyserver.effect.protocol.ProtocolManager;
import net.unesita.verifyserver.effect.protocol.ProtocolPlayer;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

public class ProtocolPacketPlayOutPlayerInfo implements ProtocolPacket{
	
	@Getter private final Class<?> classbyPacket;
	@Getter private final String name;
	@Getter private Enum<?> action;
	@Getter private Object instance;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProtocolPacketPlayOutPlayerInfo(ProtcolInfoAction action, ProtocolPlayer player) {
		this.classbyPacket = ProtocolManager.getInstance().getMinecraftServerInstance("PacketPlayOutPlayerInfo");
		this.name = "PacketPlayOutPlayerInfo";
		this.action = Enum.valueOf((Class<Enum>) ProtocolManager.getInstance().getMinecraftServerInstance("PacketPlayOutPlayerInfo$EnumPlayerInfoAction"), action.toString());
		try
		{
			this.instance = classbyPacket.getConstructor(ProtocolManager.getInstance().getMinecraftServerInstance("PacketPlayOutPlayerInfo$EnumPlayerInfoAction"), 
					Array.newInstance(ProtocolManager.getInstance().getMinecraftServerInstance("EntityPlayer")).getClass()).newInstance(this.action, player.NMS());
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
	
	public enum ProtcolInfoAction
	{
		ADD_PLAYER,
		UPDATE_GAME_MODE,
		UPDATE_LATENCY,
		UPDATE_DISPLAY_NAME,
		REMOVE_PLAYER;
	}

}
