package subaraki.pga.event;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.render.layer.LayerScreen;

@EventBusSubscriber(modid = ScreenMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
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
