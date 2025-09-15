package subaraki.pga.event;

import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.util.ScreenPackReader;

@Mod.EventBusSubscriber(modid = ScreenMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ResourceReloadEvent {
    
    @SubscribeEvent
    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
        
        event.registerReloadListener(new ScreenPackReader());
    }
    
}
