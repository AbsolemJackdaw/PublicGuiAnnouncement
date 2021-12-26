package subaraki.pga.event;

import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;
import subaraki.pga.capability.FabricScreenData;
import subaraki.pga.network.server.SPacketTracking;

public class EntityTracker {
    
    public static void registerTracking() {
        
        //too soon for login sync
        EntityTrackingEvents.START_TRACKING.register((entity, player) -> {
            if(entity instanceof ServerPlayer target && player != null) {
                FabricScreenData.get(target).ifPresent(data -> {
                    new SPacketTracking(player, target.getUUID(), data.getServerData()).send();
                });
            }
        });
    }
    
    public static void registerLogin() {
        //same issue as above
//        ServerPlayConnectionEvents.JOIN.register((packetListener, sender, mcServer) -> {
//            for(ServerPlayer playerTrackingThisPlayer : PlayerLookup.tracking(packetListener.getPlayer())) {
//                FabricScreenData.get(playerTrackingThisPlayer).ifPresent(data -> {
//                    new SPacketTracking(packetListener.player, playerTrackingThisPlayer.getUUID(), data.getServerData());
//                });
//            }
//        });
    }
    
}
