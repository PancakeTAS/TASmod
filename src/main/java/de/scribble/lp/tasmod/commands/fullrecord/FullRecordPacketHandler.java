package de.scribble.lp.tasmod.commands.fullrecord;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class FullRecordPacketHandler implements IMessageHandler<FullRecordPacket, IMessage>{

	public FullRecordPacketHandler() {
	}
	
	@Override
	public IMessage onMessage(FullRecordPacket message, MessageContext ctx) {
		if(ctx.side == Side.CLIENT) {
			Minecraft mc = Minecraft.getMinecraft();
			mc.addScheduledTask(()->{
				mc.world.sendQuittingDisconnectingPacket();
		        mc.loadWorld((WorldClient)null);
		        mc.displayGuiScreen(new GuiMainMenu());
			});
		}
		return null;
	}

}
