package subaraki.pga.mixin;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.player.PlayerModelType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import subaraki.pga.render.layer.CommonLayer;

import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public class BindRenderLayerMixin {

    @Shadow
    private Map<PlayerModelType, AvatarRenderer<AbstractClientPlayer>> playerRenderers;

    @Inject(method = "onResourceManagerReload", at = @At("RETURN"))
    public void addLayers(ResourceManager resourceManager, CallbackInfo ci) {

        playerRenderers.keySet().forEach(model -> {
            if (playerRenderers.get(model) instanceof AvatarRenderer<?> renderer) {
                ((AccessorLayers) renderer).invokeAddLayer(new CommonLayer(renderer));
            }
        });
    }
}
