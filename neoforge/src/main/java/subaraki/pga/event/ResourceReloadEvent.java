package subaraki.pga.event;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import subaraki.pga.mod.CommonScreenMod;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.util.ScreenPackReader;

@EventBusSubscriber(modid = ScreenMod.MODID)
public class ResourceReloadEvent {

    @SubscribeEvent
    public static void registerReloadListener(AddClientReloadListenersEvent event) {

        event.addListener(ResourceLocation.fromNamespaceAndPath(CommonScreenMod.MODID, "reload_key"), new ScreenPackReader());
    }

}
