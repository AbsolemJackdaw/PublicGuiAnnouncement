package subaraki.pga.network.server;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import subaraki.pga.capability.FabricScreenData;
import subaraki.pga.network.CommonChannel;

public class ServerNetwork {

    public static void register() {

        ServerPlayNetworking.registerGlobalReceiver(CommonChannel.CHANNEL, (server, player, handler, buf, responseSender) -> {

            byte packetid = buf.readByte();

            switch (packetid) {
                case CommonChannel.SPACKETSELF -> {
                    String ref = buf.readUtf(128);
                    server.execute(() -> {
                        //update server data
                        FabricScreenData.get(player).ifPresent(data -> data.setServerData(ref));
                        //send server data to our client so our player can visibly render it for us too
                        new SPacketSelf(player, ref);
                    });
                }
                case CommonChannel.SPACKETTRACKING -> {
                    String ref = buf.readUtf(128);
                    server.execute(() -> {
                        sendAround(ref, player);
                    });
                }
            }
        });
    }

    public static void sendAround(String ref, Player player) {
        //send the opened screen over to tracking players os they can render our data on our player
        for (ServerPlayer playerTrackingThisPlayer : PlayerLookup.tracking(player)) {
            //give our uuid so the other player can set our data to our model on their client
            //posibility of uuid usage here
            new SPacketTracking(playerTrackingThisPlayer, player.getUUID(), ref).send();
        }
    }

}
