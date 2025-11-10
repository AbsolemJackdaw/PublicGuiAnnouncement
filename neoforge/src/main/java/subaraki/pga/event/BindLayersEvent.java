package subaraki.pga.event;

import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import subaraki.pga.mod.CommonScreenMod;
import subaraki.pga.render.layer.CommonLayer;

@EventBusSubscriber(modid = CommonScreenMod.MODID, value = Dist.CLIENT)
public class BindLayersEvent {

    @SubscribeEvent
    public static void layers(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(model -> {
            if (event.getPlayerRenderer(model) instanceof AvatarRenderer<?> renderer)
                renderer.addLayer(new CommonLayer(renderer));
        });
    }
}
