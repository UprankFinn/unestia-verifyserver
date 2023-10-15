package net.unesita.verifyserver.effect.particle;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

public final class ParticleData {
	
	@Getter private final ParticleType type;
	@Getter @Setter private Location location;
	@Getter @Setter private boolean async;
	
	protected ParticleData(ParticleType type, Location loc){
		this.type = type;
		this.location = loc;
		this.async = false;
	}
	
	protected ParticleData(ParticleType type){
		this.type = type;
		this.location = null;
		this.async = false;
	}
	
	protected ParticleData(ParticleType type, boolean async){
		this.type = type;
		this.location = null;
		this.async = async;
	}
	
	protected ParticleData(ParticleType type, Location loc, boolean async){
		this.type = type;
		this.location = loc;
		this.async = async;
	}

}
