package de.scribble.lp.tasmod.ticksync;

import java.util.concurrent.atomic.AtomicBoolean;

import de.scribble.lp.tasmod.networking.Client;
import de.scribble.lp.tasmod.networking.packets.ServerTickSyncPacket;
import de.scribble.lp.tasmod.tickratechanger.TickrateChangerClient;
import net.minecraft.client.Minecraft;

/**
 * This class manages tick sync
 * German: https://1drv.ms/p/s!Av_ysXerhm5CphLvLvguvL5QYe1A?e=MHPldP
 * English: https://1drv.ms/p/s!Av_ysXerhm5Cpha7Qq2tiVebd4DY?e=pzxOva
 *
 * @author Pancake
 */
public class TickSyncClient {

	public static final AtomicBoolean shouldTick = new AtomicBoolean(true);
	
	/**
	 * Handles incoming tick packets from the server to the client
	 * This will simply tick the client as long as the tick is correct
	 *
	 * @param uuid Server UUID, null
	 * @param tick Current tick of the server
	 */
	public static void onPacket() {
//		TickrateChangerClient.changeClientTickrate(20);
		shouldTick.set(true);
	}

	/**
	 * Called after a client tick. This will send a packet
	 * to the server making it tick
	 *
	 * @param mc Instance of Minecraft
	 */
	public static void clientPostTick(Minecraft mc) {
//		TickrateChangerClient.changeClientTickrate(0);
		if (mc.player == null)
			return;
		Client.sendPacket(new ServerTickSyncPacket(mc.player.getGameProfile().getId()));
	}

}
