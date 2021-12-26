package subaraki.pga.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import subaraki.pga.event.ContainerEventHandler;

@Mixin(ServerPlayer.class)
public class ContainerOpenMixin {
    
    @Inject(method = "initMenu", at = @At("TAIL"))
    public void doCloseContainer(AbstractContainerMenu abstractContainerMenu, CallbackInfo ci) {
        
        ServerPlayer player = (ServerPlayer) (Object) this;
        ContainerEventHandler.openContainerEvent(player, abstractContainerMenu);
    }
    
}
