package com.minecrafttas.tasmod.commands.fullplay;

import com.minecrafttas.tasmod.ClientProxy;
import com.minecrafttas.tasmod.events.OpenGuiEvents;
import com.minecrafttas.tasmod.inputcontainer.TASstate;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FullPlayPacket implements IMessage {

	public FullPlayPacket() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	public static class FullPlayPacketHandler implements IMessageHandler<FullPlayPacket, IMessage> {

		public FullPlayPacketHandler() {
		}

		@Override
		public IMessage onMessage(FullPlayPacket message, MessageContext ctx) {
			if (ctx.side.isClient()) {
				workaround();
			}
			return null;
		}

		@SideOnly(Side.CLIENT)
		private void workaround() {
			Minecraft mc = Minecraft.getMinecraft();
			OpenGuiEvents.stateWhenOpened = TASstate.PLAYBACK;
			ClientProxy.tickSchedulerClient.add(()->{
				mc.world.sendQuittingDisconnectingPacket();
				mc.loadWorld((WorldClient) null);
				mc.displayGuiScreen(new GuiMainMenu());
			});
 		}
	}
}