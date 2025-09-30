package subaraki.pga.event;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import subaraki.pga.mod.CommonScreenMod;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.network.CPacketTracking;

@EventBusSubscriber(modid = CommonScreenMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class PlayerTracker {

    @SubscribeEvent
    public static void playerTracking(PlayerEvent.StartTracking event) {

        if (event.getTarget() instanceof ServerPlayer target) {
            if (event.getEntity() instanceof ServerPlayer me) {
                //get server sided ref
                //send ref to tracking
                var data = target.getData(ScreenMod.PGA_DATA);
                String ref = data.getServerData();
                PacketDistributor.sendToPlayer(me, new CPacketTracking(ref, target.getUUID()));
            }
        }
    }
}
