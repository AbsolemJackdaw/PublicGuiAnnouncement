package subaraki.pga.network.packet_client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import subaraki.pga.capability.ForgeScreenData;
import subaraki.pga.network.IPacketBase;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.util.ClientReferences;

import java.util.function.Supplier;

public class CPacketSync implements IPacketBase {
    
    private String name;
    
    public CPacketSync() {
    
    }
    
    public CPacketSync(String name) {
        
        this.name = name;
    }
    
    public CPacketSync(FriendlyByteBuf buf) {
        
        decode(buf);
    }
    
    @Override
    public void encode(FriendlyByteBuf buf) {
        
        buf.writeUtf(name);
    }
    
    @Override
    public void decode(FriendlyByteBuf buf) {
        
        name = buf.readUtf(128);
    }
    
    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        
        context.get().enqueueWork(() -> {
            ForgeScreenData.get(ClientReferences.getClientPlayer()).ifPresent(data -> data.setClientScreen(name));
        });
        context.get().setPacketHandled(true);
        
    }
    
    @Override
    public void encrypt(int id) {
        
        NetworkHandler.NETWORK.registerMessage(id, CPacketSync.class, CPacketSync::encode, CPacketSync::new,
                CPacketSync::handle);
    }
    
}
