package subaraki.pga.network.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import subaraki.pga.network.IPacket;
import subaraki.pga.network.server.ServerNetwork;

public class CPacketSelf implements IPacket {
    
    FriendlyByteBuf buf;
    
    public CPacketSelf(String ref) {
        
        buf = PacketByteBufs.create();
        buf.writeUtf(ref);
    }
    
    @Override
    public void send() {
        
        ClientPlayNetworking.send(ServerNetwork.UPDATE_SELF, buf);
        
    }
    
}
