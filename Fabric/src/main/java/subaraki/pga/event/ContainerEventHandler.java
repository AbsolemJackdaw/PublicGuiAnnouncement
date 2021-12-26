package subaraki.pga.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import subaraki.pga.capability.FabricScreenData;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.network.server.SPacketSelf;
import subaraki.pga.network.server.ServerNetwork;

public class ContainerEventHandler {
    
    public static void openContainerEvent(Player player, AbstractContainerMenu opened) {
        
        if(opened != null && player instanceof ServerPlayer serverPlayer) {
            String name = opened.getClass().getName();
            serverUpdateAndSend(serverPlayer, name);
        }
    }
    
    public static void closeContainerEvent(Player player, AbstractContainerMenu closed) {
        
        if(player instanceof ServerPlayer serverPlayer && closed != null) {
            serverUpdateAndSend(serverPlayer, ScreenData.CLOSE_SCREEN);
        }
    }
    
    private static void serverUpdateAndSend(ServerPlayer serverPlayer, String name) {
        
        FabricScreenData.get(serverPlayer).ifPresent(data -> {
            data.setServerData(name);
        });
        ServerNetwork.sendAround(name, serverPlayer);
        new SPacketSelf(serverPlayer, name).send();
    
    }
    
}
