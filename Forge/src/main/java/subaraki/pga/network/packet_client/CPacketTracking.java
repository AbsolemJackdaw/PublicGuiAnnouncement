package subaraki.pga.network.packet_client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import subaraki.pga.capability.ForgeScreenData;
import subaraki.pga.network.IPacketBase;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.util.ClientReferences;

import java.util.UUID;
import java.util.function.Supplier;

public class CPacketTracking implements IPacketBase {

    private UUID uuid;
    private String name;

    public CPacketTracking() {

    }

    public CPacketTracking(FriendlyByteBuf buf) {

        decode(buf);
    }

    public CPacketTracking(UUID uuid, String name) {

        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {

        buf.writeUtf(name);
        buf.writeUUID(uuid);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {

        name = buf.readUtf();
        uuid = buf.readUUID();
    }

    @Override
    public void handle(Supplier<Context> context) {

        context.get().enqueueWork(() -> {
            ForgeScreenData.get(ClientReferences.getClientPlayerByUUID(uuid))
                    .ifPresent(screenData -> screenData.setClientScreen(name));
        });
        context.get().setPacketHandled(true);
    }

    @Override
    public void encrypt(int id) {

        NetworkHandler.NETWORK.registerMessage(id, CPacketTracking.class, CPacketTracking::encode,
                CPacketTracking::new, CPacketTracking::handle);
    }

}
