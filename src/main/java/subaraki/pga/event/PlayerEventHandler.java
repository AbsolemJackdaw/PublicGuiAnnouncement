package subaraki.pga.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_server.PacketSendScreenToTrackingPlayers;

public class PlayerEventHandler {

    @SubscribeEvent
    public void playerTracking(PlayerEvent.StartTracking event) {

        if(event.getEntity().world.isRemote)
            return;
        
        if (event.getPlayer() != null) {
            PlayerEntity player = event.getPlayer();
            ScreenData data = ScreenData.get(player);
            
            if (data.getViewingScreen() != null)
                NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY.with(() -> player),
                        new PacketSendScreenToTrackingPlayers(player.getUniqueID(), data.getViewingScreen().getRefName()));
        }
    }
}
