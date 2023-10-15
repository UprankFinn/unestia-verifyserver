package net.unesita.verifyserver.effect.protocol.util;

import lombok.Getter;
import net.unesita.verifyserver.effect.protocol.ProtocolInfo;
import net.unesita.verifyserver.effect.protocol.ProtocolManager;

import java.lang.reflect.InvocationTargetException;

public class ProtocolBlockPosition implements ProtocolInfo {
	
	@Getter private String name;
	@Getter private Object instance;
	@Getter private final double x;
	@Getter private final double y;
	@Getter private final double z;
	@Getter private final int blockX;
	@Getter private final int blockY;
	@Getter private final int blockZ;
	
	public ProtocolBlockPosition(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockX = (int)x;
		this.blockY = (int)y;
		this.blockZ = (int)z;
		this.name = "BlockPosition";
		try
		{
			this.instance = ProtocolManager.getInstance().getMinecraftServerInstance("BlockPosition").getConstructor(double.class, double.class, double.class).newInstance(x, y, z);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
	}
	
	public ProtocolBlockPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockX = (int)x;
		this.blockY = (int)y;
		this.blockZ = (int)z;
		this.name = "BlockPosition";
		try
		{
			this.instance = ProtocolManager.getInstance().getMinecraftServerInstance("BlockPosition").getConstructor(int.class, int.class, int.class).newInstance(x, y, z);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
	}

}
