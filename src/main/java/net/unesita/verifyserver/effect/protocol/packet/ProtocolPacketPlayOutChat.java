package net.unesita.verifyserver.effect.protocol.packet;

import lombok.Getter;
import net.unesita.verifyserver.effect.protocol.ProtocolManager;
import net.unesita.verifyserver.effect.protocol.util.ProtocolChatComponentText;

import java.lang.reflect.InvocationTargetException;

public class ProtocolPacketPlayOutChat implements ProtocolPacket{
	private Class<?> clazz;
	@Getter
	private ProtocolChatComponentText component;
	private Object obj;
	
	
	public ProtocolPacketPlayOutChat(ProtocolChatComponentText component, byte value){
		clazz = ProtocolManager.getInstance().getMinecraftServerInstance("PacketPlayOutChat");
		this.component = component;
		try
		{
			this.obj = clazz.getConstructor(ProtocolManager.getInstance().getMinecraftServerInstance("IChatBaseComponent"), byte.class).newInstance(component.getInstance(), value);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public String getName()
	{
		return "PacketPlayOutChat";
	}


	@Override
	public Class<?> getClassbyPacket()
	{
		return clazz;
	}


	@Override
	public Object getInstance()
	{
		return obj;
	}

}
