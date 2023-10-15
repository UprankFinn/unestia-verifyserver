package net.unesita.verifyserver.effect.protocol.util;

import net.unesita.verifyserver.effect.protocol.packet.ProtocolPacket;

public interface ProtocolPacketProvider {
	
	ProtocolPacket[] getPackets();
	
}
