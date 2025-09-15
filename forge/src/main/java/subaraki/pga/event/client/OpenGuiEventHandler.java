package subaraki.pga.event.client;

import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.pga.capability.ForgeScreenData;
import subaraki.pga.event.CommonGuiOpenEvent;
import subaraki.pga.mod.CommonScreenMod;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_server.SPacketSync;
import subaraki.pga.network.packet_server.SPacketTracking;
import subaraki.pga.util.ClientReferences;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = CommonScreenMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class OpenGuiEventHandler {

    @SubscribeEvent
    public static void openGuiEvent(ScreenEvent.Opening event) {
    }

    @SubscribeEvent
    public static void closingGuiEvent(ScreenEvent.Closing event) {
        //not doing what it should do.
        //using mixin instead : GuiClosedMixin.class
    }

    public static void openGui(Screen screen) {
        sendUpdateScreenPacket(screen);

    }

    public static void closeGui() {
        sendUpdateScreenPacket(null);
    }

    private static void sendUpdateScreenPacket(Screen screen) {
        // minecraft sets screens twice to null for closing them.
        // no current workaround to reduce packet spam
        if (ClientReferences.getClientPlayer() != null) {
            String resultName = CommonGuiOpenEvent.onOpen(
                    ForgeScreenData.get(ClientReferences.getClientPlayer()).resolve(), screen);
            NetworkHandler.NETWORK.sendToServer(new SPacketSync(resultName));
            UUID uuid = ClientReferences.getClientPlayer().getUUID();

            NetworkHandler.NETWORK.sendToServer(new SPacketTracking(uuid, resultName));
        }
    }

}
