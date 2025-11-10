package subaraki.pga.event.client;

import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import subaraki.pga.event.CommonGuiOpenEvent;
import subaraki.pga.mod.CommonScreenMod;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.util.ClientReferences;

@EventBusSubscriber(modid = CommonScreenMod.MODID, value = Dist.CLIENT)
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
        var player = ClientReferences.getClientPlayer();
        if (player != null) {
            String resultName = CommonGuiOpenEvent.onOpen(player.getData(ScreenMod.PGA_DATA.get()), screen);
            ClientPacketDistributor.sendToServer(new subaraki.pga.network.SPacketSelf(resultName));
            ClientPacketDistributor.sendToServer(new subaraki.pga.network.SPacketTracking(resultName, player.getUUID()));
        }
    }

}
