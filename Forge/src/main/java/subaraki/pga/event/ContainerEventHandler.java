package subaraki.pga.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_client.PacketSendScreenToClient;
import subaraki.pga.network.packet_client.PacketSendScreenToTrackingPlayers;

@Mod.EventBusSubscriber(modid = ScreenMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ContainerEventHandler {

    @SubscribeEvent
    public static void openContainerEvent(PlayerContainerEvent.Open event) {

        if (event.getContainer() != null && !event.getPlayer().level.isClientSide) {
            String name = event.getContainer().getClass().getName();
            sendUpdateScreenPacket((ServerPlayer) event.getPlayer(), name);
        }
    }

    @SubscribeEvent
    public static void closeContainerEvent(PlayerContainerEvent.Close event) {

        if (event.getContainer() != null && event.getPlayer() != null && !event.getPlayer().level.isClientSide) {
            sendUpdateScreenPacket((ServerPlayer) event.getPlayer(), ScreenData.CLOSE_SCREEN);
        }
    }

    private static void sendUpdateScreenPacket(ServerPlayer player, String name) {
        NetworkHandler.NETWORK.send(PacketDistributor.PLAYER.with(() -> player),
                new PacketSendScreenToClient(name));

        NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY.with(() -> player),
                new PacketSendScreenToTrackingPlayers(player.getUUID(), name));
    }
}
