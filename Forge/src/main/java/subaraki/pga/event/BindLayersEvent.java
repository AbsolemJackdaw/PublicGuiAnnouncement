package subaraki.pga.event;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;
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
    public static void layers(EntityRenderersEvent.AddLayers event)
    {

        String types[] = new String[] { "default", "slim" };
        for (String type : types)
        {
            PlayerRenderer renderer =  event.getSkin(type);
            renderer.addLayer(new LayerScreen(renderer));
        }
    }

}
