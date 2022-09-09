package subaraki.pga.network;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import subaraki.pga.network.packet_client.CPacketTracking;
import subaraki.pga.network.packet_server.SPacketSync;
import subaraki.pga.network.packet_server.SPacketTracking;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel NETWORK = NetworkRegistry.ChannelBuilder
            .named(CommonChannel.CHANNEL)
            .clientAcceptedVersions(NetworkRegistry.acceptMissingOr(PROTOCOL_VERSION::equals))
            .serverAcceptedVersions(NetworkRegistry.acceptMissingOr(PROTOCOL_VERSION::equals))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void register() {
        new CPacketTracking().encrypt(CommonChannel.CPACKETTRACKING);
        new SPacketSync().encrypt(CommonChannel.SPACKETSELF);
        new SPacketTracking().encrypt(CommonChannel.SPACKETTRACKING);
    }
}
