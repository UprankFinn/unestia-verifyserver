package net.unesita.verifyserver.effect.protocol.packet;

import lombok.Getter;
import lombok.Setter;
import net.unesita.verifyserver.effect.particle.ParticleType;
import net.unesita.verifyserver.effect.protocol.ProtocolManager;
import org.bukkit.Location;

import java.lang.reflect.InvocationTargetException;

public class ProtocolPacketPlayOutWorldParticles implements ProtocolPacket{
	
	@Getter private Object instance;
	@Getter private Class<?> classbyPacket;
	@Getter private String name;
	@Getter @Setter private Location location;
	@Getter private Enum<?> particleEnum;
	@Getter private final ParticleType type;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProtocolPacketPlayOutWorldParticles(Location loc, ParticleType type) {
		
		this.location = loc;
		this.type = type;
		this.name = "PacketPlayOutWorldParticles";
		this.classbyPacket = ProtocolManager.getInstance().getMinecraftServerInstance("PacketPlayOutWorldParticles");
		this.particleEnum = Enum.valueOf((Class<Enum>) ProtocolManager.getInstance().getMinecraftServerInstance("EnumParticle"), type.toString());
		try
		{
			this.instance = classbyPacket.getConstructor(new Class<?>[0]).newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		
		  ProtocolManager.getInstance().setField(instance, instance.getClass(), "a", particleEnum);
		  ProtocolManager.getInstance().setField(instance, instance.getClass(), "b", (float)location.getX());
		  ProtocolManager.getInstance().setField(instance, instance.getClass(), "c", (float)location.getY());
		  ProtocolManager.getInstance().setField(instance, instance.getClass(), "d", (float)location.getZ());
		
	}
	
	public void setOffSetX(float offsetx)
	{
		  ProtocolManager.getInstance().setField(instance, instance.getClass(), "e", offsetx);
	}
	
	public void setOffSetY(float offsety)
	{
		  ProtocolManager.getInstance().setField(instance, instance.getClass(), "f", offsety);
	}
	
	public void setOffSetZ(float offsetz)
	{
		  ProtocolManager.getInstance().setField(instance, instance.getClass(), "g", offsetz);
	}
	
	public void setSpeed(float speed)
	{
		  ProtocolManager.getInstance().setField(instance, instance.getClass(), "h", speed);
	}
	
	public void setAmout(int amout)
	{
		  ProtocolManager.getInstance().setField(instance, instance.getClass(), "i", amout);
	}

}
