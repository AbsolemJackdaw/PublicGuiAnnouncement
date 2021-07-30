package subaraki.pga.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_client.PacketSendScreenToClient;

public class ContainerEventHandler {

    @SubscribeEvent
    public void openContainerEvent(PlayerContainerEvent.Open event) {

        if (event.getContainer() != null && !event.getPlayer().world.isRemote) {
            String name = event.getContainer().getClass().getName();
            ScreenData.get(event.getPlayer()).ifPresent(t -> t.setViewingScreen(name));
            NetworkHandler.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PacketSendScreenToClient(name));
        }

    }

    @SubscribeEvent
    public void closeContainerEvent(PlayerContainerEvent.Close event) {

        if (event.getContainer() != null && event.getPlayer() != null && !event.getPlayer().world.isRemote) {
            ScreenData.get(event.getPlayer()).ifPresent(t -> t.setViewingScreen(ScreenData.CLOSE_SCREEN));
            NetworkHandler.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()),
                    new PacketSendScreenToClient(ScreenData.CLOSE_SCREEN));
        }
    }
}
