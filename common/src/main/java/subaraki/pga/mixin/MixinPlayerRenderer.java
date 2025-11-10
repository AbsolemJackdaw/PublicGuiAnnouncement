package subaraki.pga.mixin;

import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import subaraki.pga.capability.IPGAState;

@Mixin(AvatarRenderer.class)
public class MixinPlayerRenderer<AvatarlikeEntity extends Avatar & ClientAvatarEntity> {

    @Inject(method = "Lnet/minecraft/client/renderer/entity/player/AvatarRenderer;extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("HEAD"))
    public void pga$extractRenderState(AvatarlikeEntity entity, AvatarRenderState reusedState, float partialTick, CallbackInfo ci) {

        if (reusedState instanceof IPGAState state) {
            state.pga$setData((Player) entity);
        }
    }
}
