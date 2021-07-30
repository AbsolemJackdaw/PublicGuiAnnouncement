package subaraki.pga.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_server.PacketSendScreenToTrackingPlayers;

public class PlayerEventHandler {

    @SubscribeEvent
    public void playerTracking(PlayerEvent.StartTracking event) {

        if (event.getEntity().level.isClientSide)
            return;

        if (event.getPlayer() != null) {
            Player player = event.getPlayer();

            ScreenData.get(player).ifPresent(data -> {

                if (data.getViewingScreen() == null)
                    return;

                String name = data.getViewingScreen().getRefName();

                NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY.with(() -> player),
                        new PacketSendScreenToTrackingPlayers(player.getUUID(), name));
            });
        }
    }
}
