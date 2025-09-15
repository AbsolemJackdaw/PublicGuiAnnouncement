package subaraki.pga.capability;

import dev.onyxstudios.cca.api.v3.component.TransientComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class FabricScreenData extends ScreenData implements TransientComponent {
    
    
    public FabricScreenData(Player player) {
        
        setPlayer(player);
    }
    
    public static Optional<FabricScreenData> get(Player player) {
        
        return Components.DATA.maybeGet(player);
    }
    
    //solves upcasting issue with server packet
    public static Optional<FabricScreenData> get(LocalPlayer player) {
        
        return Components.DATA.maybeGet(player);
    }
    
}
