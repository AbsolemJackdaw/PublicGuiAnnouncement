package subaraki.pga.mixin;

import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import subaraki.pga.capability.FabricScreenData;
import subaraki.pga.network.server.SPacketTracking;

@Mixin(ServerEntity.class)
abstract class EntityTracker {
    @Shadow
    @Final
    private Entity entity;

    @Inject(method = "addPairing", at = @At("TAIL"))
    private void onStartTracking(ServerPlayer player, CallbackInfo ci) {
        if (entity instanceof ServerPlayer target && player != null) {
            FabricScreenData.get(target).ifPresent(data -> {
                new SPacketTracking(player, target.getUUID(), data.getServerData()).send();
            });
        }
    }
}

