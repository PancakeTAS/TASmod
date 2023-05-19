package com.minecrafttas.tasmod.events;


import java.io.IOException;
import java.net.ConnectException;

import com.minecrafttas.tasmod.TASmodClient;
import com.minecrafttas.tasmod.TASmod;
import com.minecrafttas.tasmod.networking.TASmodNetworkClient;
import com.minecrafttas.tasmod.playback.server.InitialSyncStatePacket;
import com.minecrafttas.tasmod.tickratechanger.TickrateChangerServer;
import com.mojang.authlib.GameProfile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;

public class PlayerJoinLeaveEvents {
	
	/**
	 * Executes when a player joined the server on the server side
	 * 
	 * @param player The player that joined the server
	 */
	public static void firePlayerJoinedServerSide(EntityPlayerMP player) {
		TASmod.logger.info("Firing login events for {} on the SERVER", player.getName());
		TickrateChangerServer.joinServer(player);
	}
	
	/**
	 * Executes when a player left the server on the server side
	 * 
	 * @param player The player that left the server
	 */
	public static void firePlayerLeaveServerSide(EntityPlayerMP player) {
//		TASmod.logger.info("Firing logout events for {} on the SERVER", player.getName());
		TASmod.containerStateServer.leaveServer(player);
	}

	/**
	 * Executes when the player joins the server on the client side
	 * @param player The singleplayer player
	 * @throws ConnectException 
	 */
	@Environment(EnvType.CLIENT)
	public static void firePlayerJoinedClientSide(net.minecraft.client.entity.EntityPlayerSP player) throws ConnectException {
		TASmod.logger.info("Firing login events for {} on the CLIENT", player.getName());
		
		Minecraft mc = Minecraft.getMinecraft();
		
		if(mc.isIntegratedServerRunning())
			TASmodClient.packetClient = new TASmodNetworkClient(TASmod.logger);
		else {
			String full = mc.getCurrentServerData().serverIP;
			String[] fullsplit = full.split(":");
			if(fullsplit.length == 1) {
				TASmodClient.packetClient = new TASmodNetworkClient(TASmod.logger, full, 3111);
			} else if(fullsplit.length == 2){
				String ip = fullsplit[0];
				TASmodClient.packetClient = new TASmodNetworkClient(TASmod.logger, ip, 3111);
			} else {
				throw new ConnectException("Something went wrong while connecting. The ip seems to be wrong");
			}
		}
		
		TASmodClient.packetClient.sendToServer(new InitialSyncStatePacket(TASmodClient.virtual.getContainer().getState()));
		
		TASmodClient.virtual.unpressNext();
		TASmodClient.shieldDownloader.onPlayerJoin(player.getGameProfile());
//		TickrateChangerClient.joinServer(); //TODO Only the first player joining the server should be able to change the tickrate
		TASmod.ktrngHandler.setInitialSeed();
		
	}
	
	/**
	 * Executes when the player leaves the server on the client side
	 * @param player
	 */
	@Environment(EnvType.CLIENT)
	public static void firePlayerLeaveClientSide(net.minecraft.client.entity.EntityPlayerSP player) {
		TASmod.logger.info("Firing logout events for {} on the CLIENT", player.getName());
		try {
			if(TASmodClient.packetClient!=null) {
				TASmodClient.packetClient.killClient();
				TASmodClient.packetClient=null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * When any player joins the world on the client
	 * @param profile
	 */
	public static void fireOtherPlayerJoinedClientSide(GameProfile profile) {
		TASmod.logger.info("Firing other login events for {} on the CLIENT", profile.getName());
		TASmodClient.shieldDownloader.onPlayerJoin(profile);
	}
}
