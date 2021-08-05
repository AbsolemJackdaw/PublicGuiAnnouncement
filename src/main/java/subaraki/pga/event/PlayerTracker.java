package subaraki.pga.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_client.PacketGetScreenFromClient;

@Mod.EventBusSubscriber(modid = ScreenMod.MODID, bus = Bus.FORGE)
public class PlayerTracker {

    @SubscribeEvent
    public static void playerTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof Player) {
            if (event.getPlayer() instanceof ServerPlayer) {
                ServerPlayer player = (ServerPlayer) event.getPlayer();
                //screen data isnt present on the server.
                //send a packet to the client first to retrieve data and send to tracked players
                NetworkHandler.NETWORK.send(PacketDistributor.PLAYER.with(() -> player), new PacketGetScreenFromClient());
            }
        }
    }
}
