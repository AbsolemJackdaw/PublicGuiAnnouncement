package subaraki.pga.network.packet_server;

import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.network.IPacketBase;
import subaraki.pga.network.NetworkHandler;

public class PacketSendScreenToServer implements IPacketBase {

    public PacketSendScreenToServer() {

    }

    private String name;

    public PacketSendScreenToServer(String name) {

        this.name = name;

    }

    // decode
    public PacketSendScreenToServer(PacketBuffer buf) {

        decode(buf);
    }

    @Override
    public void encode(PacketBuffer buf) {

        buf.writeString(name);
    }

    @Override
    public void decode(PacketBuffer buf) {

        name = buf.readString(128);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {

        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();

            ScreenData.get(player).ifPresent(t -> t.setViewingScreen(name));

        });
        context.get().setPacketHandled(true);
    }

    public void encrypt(int id) {

        NetworkHandler.NETWORK.registerMessage(id, PacketSendScreenToServer.class, PacketSendScreenToServer::encode, PacketSendScreenToServer::new,
                PacketSendScreenToServer::handle);

    }

}
