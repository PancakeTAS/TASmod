package com.minecrafttas.tasmod.ktrng;

import com.minecrafttas.tasmod.ClientProxy;
import com.minecrafttas.tasmod.TASmod;

import de.scribble.lp.killtherng.KillTheRNG;
import de.scribble.lp.killtherng.SeedingModes;
import de.scribble.lp.killtherng.networking.ChangeSeedPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Easy access to the KillTheRNG library without littering the rest of the code
 * 
 * @author Scribble
 *
 */
public class KillTheRNGHandler{
	
	private boolean isLoaded;
	
	/**
	 * Instantiates a KillTheRNGHandler instance
	 * @param isLoaded If the KillTheRNG mod is loaded
	 */
	public KillTheRNGHandler(boolean isLoaded) {
		
		this.isLoaded=isLoaded;
		
		if (isLoaded) {
			KillTheRNG.LOGGER.info("Connection established with TASmod");
			KillTheRNG.isLibrary=true;
			KillTheRNG.mode=SeedingModes.TickChange;
			
			KillTheRNG.annotations.register(new KTRNGMonitor());
		}else {
			TASmod.logger.info("KillTheRNG doesn't appear to be loaded");
		}
	}
	
	public long advanceGlobalSeedServer() {
		if(isLoaded()) {
			return KillTheRNG.commonRandom.nextSeed();
		} else {
			return 0;
		}
	}
	
	public long getGlobalSeedServer() {
		if(isLoaded()) {
			return KillTheRNG.commonRandom.GlobalServer.getSeed();
		} else {
			return 0;
		}
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}
	
	
	//=================================================Setting the seed
	/**
	 * @return The global seed of the client
	 */
	@SideOnly(Side.CLIENT)
	public long getGlobalSeedClient() {
		if(isLoaded()) 
			return KillTheRNG.clientRandom.GlobalClient.getSeed();
		else
			return 0;
	}
	
	/**
	 * Set the global seed on the client
	 * @param seedIn The seed on the client
	 */
	@SideOnly(Side.CLIENT)
	public void setGlobalSeedClient(long seedIn) {
		if (isLoaded()) {
			KillTheRNG.clientRandom.setSeedAll(seedIn);
		}
	}
	
	/**
	 * Sends a packet to the server, setting the global seed
	 * @param seedIn The seed on the server
	 */
	@SideOnly(Side.CLIENT)
	public void setGlobalSeedServer(long seedIn) {
		if(isLoaded()) {
			if(Minecraft.getMinecraft().getConnection()!=null)
				KillTheRNG.NETWORK.sendToServer(new ChangeSeedPacket(seedIn));
			else
				setGlobalSeedClient(seedIn);
		}
	}
	//=================================================TASmod integration
	
	/**
	 * Executed every tick.
	 */
	@SideOnly(Side.CLIENT)
	public void updateClient() {
	}
	
	/**
	 * Executed every tick on the server
	 */
	public void updateServer() {
		if(isLoaded()) {
			TASmod.packetServer.sendToAll(new KTRNGSeedPacket(advanceGlobalSeedServer()));
		}
	}
	
	//================================================= Seedsync
	
	public void broadcastStartSeed() {
		if(isLoaded()) {
			long seed = getGlobalSeedServer();
			TASmod.packetServer.sendToAll(new KTRNGStartSeedPacket(seed));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void setInitialSeed() {
		ClientProxy.packetClient.sendToServer(new KTRNGSeedPacket(getGlobalSeedClient()));	// TODO Every new player in multiplayer will currently send the initial seed, which is BAD
	}

}