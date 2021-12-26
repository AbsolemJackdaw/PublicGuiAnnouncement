package subaraki.pga.network.packet_server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import subaraki.pga.capability.ForgeScreenData;
import subaraki.pga.network.IPacketBase;
import subaraki.pga.network.NetworkHandler;

import java.util.function.Supplier;

public class SPacketSync implements IPacketBase {
    
    String ref;
    
    public SPacketSync() {
    
    }
    
    public SPacketSync(FriendlyByteBuf buf) {
        
        decode(buf);
        
    }
    
    public SPacketSync(String ref) {
        
        this.ref = ref;
    }
    
    @Override
    public void encode(FriendlyByteBuf buf) {
        
        buf.writeUtf(ref, 128);
    }
    
    @Override
    public void decode(FriendlyByteBuf buf) {
        
        this.ref = buf.readUtf(128);
    }
    
    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            ForgeScreenData.get(player).ifPresent(data -> {
                data.setServerData(ref);
            });
        });
        context.get().setPacketHandled(true);
    }
    
    @Override
    public void encrypt(int id) {
        
        NetworkHandler.NETWORK.registerMessage(id, SPacketSync.class, SPacketSync::encode, SPacketSync::new, SPacketSync::handle);
    }
    
}
