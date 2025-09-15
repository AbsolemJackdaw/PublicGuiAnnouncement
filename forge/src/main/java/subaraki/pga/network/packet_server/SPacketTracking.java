package subaraki.pga.network.packet_server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import subaraki.pga.network.IPacketBase;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_client.CPacketTracking;

import java.util.UUID;
import java.util.function.Supplier;

public class SPacketTracking implements IPacketBase {
    
    String ref;
    UUID uuid;
    
    public SPacketTracking() {
    
    }
    
    public SPacketTracking(FriendlyByteBuf buf) {
        
        decode(buf);
        
    }
    
    public SPacketTracking(UUID uuid, String ref) {
        
        this.uuid = uuid;
        this.ref = ref;
    }
    
    @Override
    public void encode(FriendlyByteBuf buf) {
        
        buf.writeUtf(ref, 128);
        buf.writeUUID(uuid);
    }
    
    @Override
    public void decode(FriendlyByteBuf buf) {
        
        this.ref = buf.readUtf(128);
        this.uuid = buf.readUUID();
    }
    
    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        
        context.get().enqueueWork(() -> {
            NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY.with(() -> context.get().getSender()),
                    new CPacketTracking(uuid, ref));
        });
        context.get().setPacketHandled(true);
    }
    
    @Override
    public void encrypt(int id) {
        
        NetworkHandler.NETWORK.registerMessage(id, SPacketTracking.class, SPacketTracking::encode, SPacketTracking::new, SPacketTracking::handle);
    }
    
}
