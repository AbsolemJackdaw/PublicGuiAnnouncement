package subaraki.pga.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import subaraki.pga.mod.CommonScreenMod;
import subaraki.pga.network.packet_client.CPacketSync;
import subaraki.pga.network.packet_client.CPacketTracking;
import subaraki.pga.network.packet_server.SPacketSync;
import subaraki.pga.network.packet_server.SPacketTracking;

public class NetworkHandler {
    
    private static final String PROTOCOL_VERSION = "1";
    
    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation(CommonScreenMod.MODID, "pga_network"), () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    
    public static void register() {
        
        int messageID = 0;
        new CPacketSync().encrypt(messageID++);
        new CPacketTracking().encrypt(messageID++);
        new SPacketSync().encrypt(messageID++);
        new SPacketTracking().encrypt(messageID++);
        
    }
    
}
