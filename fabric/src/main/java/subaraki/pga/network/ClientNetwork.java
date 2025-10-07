package subaraki.pga.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.world.entity.player.Player;
import subaraki.pga.capability.FabricScreenData;

public class ClientNetwork {

    public static void registerPayloadHandler() {
        ClientPlayNetworking.registerGlobalReceiver(CommonChannel.CPACKETSELF_TYPE, (payload, context) -> {
            context.client().execute(() -> {
                FabricScreenData.get(context.player()).ifPresent(data -> {
                    data.setClientScreen(payload.uiScreenRef());
                });
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(CommonChannel.CPACKETTRACKING_TYPE, (payload, context) -> {
            context.client().execute(() -> {
                Player other = context.player().level().getPlayerByUUID(payload.otherPlayer());
                if (other != null) FabricScreenData.get(other).ifPresent(data -> {
                    data.setClientScreen(payload.uiScreenRef());
                });
            });
        });
    }
}
