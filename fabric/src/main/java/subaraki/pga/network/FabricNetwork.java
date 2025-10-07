package subaraki.pga.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class FabricNetwork {

    public static void registerPackets() {
        PayloadTypeRegistry.playS2C().register(CommonChannel.CPACKETSELF_TYPE, CommonChannel.CPACKETSELF_CODEC);
        PayloadTypeRegistry.playS2C().register(CommonChannel.CPACKETTRACKING_TYPE, CommonChannel.CPACKETTRACKING_CODEC);
        PayloadTypeRegistry.playC2S().register(CommonChannel.SPACKETSELF_TYPE, CommonChannel.SPACKETSELF_CODEC);
        PayloadTypeRegistry.playC2S().register(CommonChannel.SPACKETTRACKING_TYPE, CommonChannel.SPACKETTRACKING_CODEC);

    }

    public static void registerServer() {
        ServerNetwork.registerPayloadHandler();
    }

    public static void registerClient() {
        ClientNetwork.registerPayloadHandler();
    }
}
