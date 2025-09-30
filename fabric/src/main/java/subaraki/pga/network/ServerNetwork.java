package subaraki.pga.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import subaraki.pga.capability.FabricScreenData;

public class ServerNetwork {

    public static void register() {
        PayloadTypeRegistry.playC2S().register(CommonChannel.SPACKETSELF_TYPE, CommonChannel.SPACKETSELF_CODEC);
        PayloadTypeRegistry.playC2S().register(CommonChannel.SPACKETTRACKING_TYPE, CommonChannel.SPACKETTRACKING_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(CommonChannel.SPACKETSELF_TYPE, (payload, context) -> {
            context.server().execute(() -> {
                FabricScreenData.get(context.player()).ifPresent(data ->
                        data.setServerData(payload.uiScreenRef()));
                //send server data to our client so our player can visibly render it for us too
                ServerPlayNetworking.send(context.player(), new CPacketSelf(payload.uiScreenRef()));
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(CommonChannel.SPACKETTRACKING_TYPE, (payload, context) ->
                context.server().execute(() -> {
                    context.server().execute(() -> {
                        sendAround(payload.uiScreenRef(), context.player());
                    });
                })
        );
    }

    private static void sendAround(String ref, Player player) {
        //send the opened screen over to tracking players os they can render our data on our player
        for (ServerPlayer playerTrackingThisPlayer : PlayerLookup.tracking(player)) {
            //give our uuid so the other player can set our data to our model on their client
            //posibility of uuid usage here
            ServerPlayNetworking.send(playerTrackingThisPlayer, new CPacketTracking(ref, player.getUUID()));
        }
    }

}
