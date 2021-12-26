package subaraki.pga.event.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.pga.capability.ForgeScreenData;
import subaraki.pga.event.CommonGuiOpenEvent;
import subaraki.pga.mod.CommonScreenMod;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_server.SPacketSync;
import subaraki.pga.network.packet_server.SPacketTracking;
import subaraki.pga.util.ClientReferences;

@Mod.EventBusSubscriber(modid = CommonScreenMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class OpenGuiEventHandler {
    
    @SubscribeEvent
    public static void openGuiEvent(ScreenOpenEvent event) {
        
        // minecraft sets screens twice to null for closing them.
        // no current workaround to reduce packet spam
        if(ClientReferences.getClientPlayer() != null) {
            String resultName = CommonGuiOpenEvent.onOpen(ForgeScreenData.get(ClientReferences.getClientPlayer())
                    .resolve(), event.getScreen());
            
            NetworkHandler.NETWORK.sendToServer(new SPacketSync(resultName));
            NetworkHandler.NETWORK.sendToServer(new SPacketTracking(ClientReferences.getClientPlayer().getUUID(), resultName));
        }
    }
    
}
