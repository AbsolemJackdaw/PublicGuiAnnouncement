package subaraki.pga.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.PacketDistributor;
import subaraki.pga.capability.ForgeScreenData;
import subaraki.pga.mod.CommonScreenMod;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_client.CPacketTracking;

@Mod.EventBusSubscriber(modid = CommonScreenMod.MODID, bus = Bus.FORGE)
public class PlayerTracker {
    
    @SubscribeEvent
    public static void playerTracking(PlayerEvent.StartTracking event) {
        
        if(event.getTarget() instanceof ServerPlayer target) {
            if(event.getEntity() instanceof ServerPlayer me) {
                //get server sided ref
                //send ref to tracking
                ForgeScreenData.get(target).ifPresent(data -> {
                    
                    String ref = data.getServerData();
                    
                    NetworkHandler.NETWORK.send(PacketDistributor.PLAYER.with(() -> me), new CPacketTracking(target.getUUID(), ref));
                    
                });
            }
        }
    }
}
