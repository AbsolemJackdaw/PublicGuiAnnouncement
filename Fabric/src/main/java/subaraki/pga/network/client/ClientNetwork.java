package subaraki.pga.network.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.entity.player.Player;
import subaraki.pga.capability.FabricScreenData;
import subaraki.pga.network.CommonChannel;

import java.util.UUID;

public class ClientNetwork {

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(CommonChannel.CHANNEL, (client, handler, buf, responseSender) -> {
            byte packetid = buf.readByte();
            switch (packetid) {
                case CommonChannel.CPACKETSELF -> {
                    String ref = buf.readUtf(128);
                    client.execute(() -> {
                        FabricScreenData.get(client.player).ifPresent(data -> {
                            data.setClientScreen(ref);
                        });
                    });
                }
                case CommonChannel.CPACKETTRACKING -> {
                    String ref = buf.readUtf(128);
                    UUID otherUUID = buf.readUUID();
                    client.execute(() -> {
                        if (client.level != null) {
                            Player other = client.level.getPlayerByUUID(otherUUID);
                            if (other != null) {
                                FabricScreenData.get(other).ifPresent(data -> {
                                    data.setClientScreen(ref);
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}
