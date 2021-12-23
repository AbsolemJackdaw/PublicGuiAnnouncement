package subaraki.pga.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.mod.Components;

public class ContainerEventHandler {
    
    public static void openContainerEvent(Player player, AbstractContainerMenu opened) {
        
        if(opened != null && !player.level.isClientSide) {
            String name = opened.getClass().getName();
            Components.DATA.maybeGet(player).ifPresent(data -> {
                data.setViewingScreen(name);
                Components.DATA.sync(player);
            });
        }
    }
    
    public static void closeContainerEvent(Player player, AbstractContainerMenu closed) {
        
        if(closed != null && player != null && !player.level.isClientSide) {
            Components.DATA.maybeGet(player).ifPresent(data -> {
                data.setViewingScreen(ScreenData.CLOSE_SCREEN);
                Components.DATA.sync(player);
            });
        }
    }
    
}
