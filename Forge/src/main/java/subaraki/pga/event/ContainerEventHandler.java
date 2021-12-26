package subaraki.pga.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import subaraki.pga.capability.ForgeScreenData;
import subaraki.pga.mod.CommonScreenMod;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_client.CPacketSync;
import subaraki.pga.network.packet_client.CPacketTracking;

@Mod.EventBusSubscriber(modid = CommonScreenMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ContainerEventHandler {
    
    @SubscribeEvent
    public static void openContainerEvent(PlayerContainerEvent.Open event) {
        
        if(event.getPlayer() instanceof ServerPlayer serverPlayer) {
            sendUpdateScreenPacket(serverPlayer, event.getContainer().getClass().getName());
        }
    }
    
    @SubscribeEvent
    public static void closeContainerEvent(PlayerContainerEvent.Close event) {
        
        if(event.getPlayer() instanceof ServerPlayer serverPlayer) {
            sendUpdateScreenPacket(serverPlayer, ForgeScreenData.CLOSE_SCREEN);
        }
    }
    
    private static void sendUpdateScreenPacket(ServerPlayer serverPlayer, String name) {
        //set data server side
        ForgeScreenData.get(serverPlayer).ifPresent(data -> {
            data.setServerData(name);
        });
        //send to client and tracking
        NetworkHandler.NETWORK.send(PacketDistributor.PLAYER.with(() -> serverPlayer),
                new CPacketSync(name));
        NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY.with(() -> serverPlayer),
                new CPacketTracking(serverPlayer.getUUID(), name));
    }
    
}
