package subaraki.pga.event;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import subaraki.pga.client.NeoScreenLayer;
import subaraki.pga.mod.CommonScreenMod;

@EventBusSubscriber(modid = CommonScreenMod.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class BindLayersEvent {

    @SubscribeEvent
    public static void layers(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(model -> {
            if (event.getSkin(model) instanceof LivingEntityRenderer<?, ?> renderer)
                renderer.addLayer(new NeoScreenLayer<>(event.getSkin(model)));
        });
    }

}
