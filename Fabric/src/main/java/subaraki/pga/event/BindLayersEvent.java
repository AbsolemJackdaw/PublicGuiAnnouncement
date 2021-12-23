package subaraki.pga.event;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import subaraki.pga.render.layer.LayerScreen;

public class BindLayersEvent {
    
    public static void layers(PlayerRenderer event) {
        
        String types[] = new String[] {"default", "slim"};
        for(String type : types) {
            //event.addLayer(new LayerScreen(event));
        }
    }
    
}
