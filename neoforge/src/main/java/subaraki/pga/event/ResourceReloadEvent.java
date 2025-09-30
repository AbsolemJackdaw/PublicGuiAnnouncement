package subaraki.pga.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.util.ScreenPackReader;

@EventBusSubscriber(modid = ScreenMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ResourceReloadEvent {

    @SubscribeEvent
    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {

        event.registerReloadListener(new ScreenPackReader());
    }

}
