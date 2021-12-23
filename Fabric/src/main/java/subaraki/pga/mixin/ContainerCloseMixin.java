package subaraki.pga.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import subaraki.pga.event.ContainerEventHandler;

@Mixin(MixinPlayer.class)
public class ContainerCloseMixin extends ServerPlayer {
    
    public ContainerCloseMixin(MinecraftServer minecraftServer, ServerLevel serverLevel, GameProfile gameProfile) {
        
        super(minecraftServer, serverLevel, gameProfile);
    }
    
    @Override
    public void doCloseContainer() {
        
        ContainerEventHandler.closeContainerEvent(this, containerMenu);
        super.doCloseContainer();
    }
    
}
