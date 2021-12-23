package subaraki.pga.mixin;


import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class MixinPlayer extends ServerPlayer {
    
    public MixinPlayer(MinecraftServer minecraftServer, ServerLevel serverLevel, GameProfile gameProfile) {
        
        super(minecraftServer, serverLevel, gameProfile);
    }
    
}