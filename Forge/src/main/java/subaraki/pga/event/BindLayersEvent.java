package subaraki.pga.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import subaraki.pga.mod.CommonScreenMod;
import subaraki.pga.render.layer.LayerScreen;

@EventBusSubscriber(modid = CommonScreenMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class BindLayersEvent {

    @SubscribeEvent
    public static void layers(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(skin -> event.getSkin(skin).addLayer(new LayerScreen(event.getSkin(skin))));
    }

}
