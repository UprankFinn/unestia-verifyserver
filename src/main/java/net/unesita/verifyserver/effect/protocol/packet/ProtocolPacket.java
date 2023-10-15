package net.unesita.verifyserver.effect.protocol.packet;

import net.unesita.verifyserver.effect.protocol.ProtocolInfo;

public interface ProtocolPacket 
				extends ProtocolInfo {
	
	Class<?> getClassbyPacket();
	
}
