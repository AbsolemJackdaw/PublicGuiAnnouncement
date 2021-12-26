package subaraki.pga.network.server;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import subaraki.pga.capability.FabricScreenData;
import subaraki.pga.mod.CommonScreenMod;

public class ServerNetwork {
    
    public static final ResourceLocation UPDATE_SELF = new ResourceLocation(CommonScreenMod.MODID, "server_self");
    public static final ResourceLocation UPDATE_TRACKING = new ResourceLocation(CommonScreenMod.MODID, "server_tracking");
    
    public static void register() {
        
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_SELF, (server, player, handler, buf, responseSender) -> {
            String ref = buf.readUtf();
            server.execute(() -> {
                //update server data
                FabricScreenData.get(player).ifPresent(data -> data.setServerData(ref));
                //send server data to our client so our player can visibly render it for us too
                new SPacketSelf(player, ref);
            });
            
        });
        
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_TRACKING, (server, player, handler, buf, responseSender) -> {
            String ref = buf.readUtf();
            server.execute(() -> {
                sendAround(ref, player);
            });
        });
    }
    
    public static void sendAround(String ref, Player player) {
        //send the opened screen over to tracking players os they can render our data on our player
        for(ServerPlayer playerTrackingThisPlayer : PlayerLookup.tracking(player)) {
            //give our uuid so the other player can set our data to our model on their client
            //posibility of uuid usage here
            new SPacketTracking(playerTrackingThisPlayer, player.getUUID(), ref).send();
        }
    }
    
}
