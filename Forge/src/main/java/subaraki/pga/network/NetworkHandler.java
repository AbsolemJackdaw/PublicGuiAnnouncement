package subaraki.pga.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.network.packet_client.PacketGetScreenFromClient;
import subaraki.pga.network.packet_client.PacketSendScreenToClient;
import subaraki.pga.network.packet_client.PacketSendScreenToTrackingPlayers;
import subaraki.pga.network.packet_server.PacketSendScreenToServer;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation(ScreenMod.MODID, "pga_network"), () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register() {
        int messageID = 0;
        new PacketSendScreenToServer().encrypt(messageID++);
        new PacketSendScreenToClient().encrypt(messageID++);
        new PacketSendScreenToTrackingPlayers().encrypt(messageID++);
        new PacketGetScreenFromClient().encrypt(messageID++);
    }
}
