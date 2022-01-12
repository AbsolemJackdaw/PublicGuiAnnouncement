package subaraki.pga.mixin;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import subaraki.pga.render.layer.LayerScreen;

import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public class BindRenderLayerMixin {

    @Shadow
    private Map<String, EntityRenderer<? extends Player>> playerRenderers;

    @Inject(method = "onResourceManagerReload", at = @At("RETURN"))
    public void addLayers(ResourceManager resourceManager, CallbackInfo ci) {

        playerRenderers.keySet().forEach(skinTypeName -> { //default , slim
            if (playerRenderers.get(skinTypeName) instanceof PlayerRenderer renderer) {
                ((AccessorLayers) renderer).invokeAddLayer(new LayerScreen(renderer));
            }
        });
    }
}
