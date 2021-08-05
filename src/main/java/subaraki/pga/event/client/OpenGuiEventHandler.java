package subaraki.pga.event.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_server.PacketSendScreenToServer;
import subaraki.pga.util.ClientReferences;

@Mod.EventBusSubscriber(modid = ScreenMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class OpenGuiEventHandler {

    public OpenGuiEventHandler() {

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void openGuiEvent(GuiOpenEvent event) {

        // minecraft sets screens twice to null for closing them.
        // no current workaround to reduce packet spam
        if (ClientReferences.getClientPlayer() != null) {
            String simpleName = event.getGui() == null ? ScreenData.CLOSE_SCREEN : event.getGui().getClass().getName();
            ScreenData.get(ClientReferences.getClientPlayer()).ifPresent(screenData -> screenData.setViewingScreen(simpleName));
            NetworkHandler.NETWORK.sendToServer(new PacketSendScreenToServer(simpleName));
        }
    }
}
