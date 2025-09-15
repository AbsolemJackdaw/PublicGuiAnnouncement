package subaraki.pga.mixin;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntityRenderer.class)
public interface AccessorLayers {
    
    @Invoker("addLayer")
    boolean invokeAddLayer(RenderLayer<?, ?> layer);
    
}
