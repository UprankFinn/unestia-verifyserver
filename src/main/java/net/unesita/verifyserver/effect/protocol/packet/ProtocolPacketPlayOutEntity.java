package net.unesita.verifyserver.effect.protocol.packet;

import lombok.Getter;
import net.unesita.verifyserver.effect.protocol.ProtocolManager;

import java.lang.reflect.InvocationTargetException;

public class ProtocolPacketPlayOutEntity implements ProtocolPacket{
	
	@Getter private final Class<?> classbyPacket;
	@Getter private final String name;
	@Getter private Object instance;
	@Getter private final int entityId;
	
	public ProtocolPacketPlayOutEntity(int entity_id) {
		this.entityId = entity_id;
		this.name = "PacketPlayOutEntity";
		this.classbyPacket = ProtocolManager.getInstance().getMinecraftServerInstance("PacketPlayOutEntity");
		try
		{
			this.instance = classbyPacket.getConstructor(int.class).newInstance(entity_id);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		
	}

}
