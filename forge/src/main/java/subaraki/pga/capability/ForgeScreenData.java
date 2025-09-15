package subaraki.pga.capability;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

public class ForgeScreenData extends ScreenData {
    
    public static LazyOptional<? extends ScreenData> get(Player player) {
        
        return player == null ? LazyOptional.empty() : player.getCapability(ScreenCapability.CAPABILITY, null);
    }
    
}