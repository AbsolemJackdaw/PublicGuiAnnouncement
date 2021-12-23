package subaraki.pga.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerPlayer.class)
public interface AccessorInitMenu {
    
    @Invoker("initMenu")
    void initMenu(AbstractContainerMenu abstractContainerMenu);
    
}
