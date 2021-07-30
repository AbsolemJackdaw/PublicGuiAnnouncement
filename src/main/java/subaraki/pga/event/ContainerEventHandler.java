package subaraki.pga.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_client.PacketSendScreenToClient;

public class ContainerEventHandler {

    @SubscribeEvent
    public void openContainerEvent(PlayerContainerEvent.Open event) {

        if (event.getContainer() != null && !event.getPlayer().level.isClientSide) {
            String name = event.getContainer().getClass().getName();
            ScreenData.get(event.getPlayer()).ifPresent(t -> t.setViewingScreen(name));
            NetworkHandler.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()), new PacketSendScreenToClient(name));
        }

    }

    @SubscribeEvent
    public void closeContainerEvent(PlayerContainerEvent.Close event) {

        if (event.getContainer() != null && event.getPlayer() != null && !event.getPlayer().level.isClientSide) {
            ScreenData.get(event.getPlayer()).ifPresent(t -> t.setViewingScreen(ScreenData.CLOSE_SCREEN));
            NetworkHandler.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()),
                    new PacketSendScreenToClient(ScreenData.CLOSE_SCREEN));
        }
    }
}
