package subaraki.pga.network.packet_server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import subaraki.pga.network.IPacketBase;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_client.PacketSendScreenToTrackingPlayers;

import java.util.function.Supplier;

public class PacketSendScreenToServer implements IPacketBase {

    public PacketSendScreenToServer() {

    }

    private String name;

    public PacketSendScreenToServer(String name) {

        this.name = name;

    }

    // decode
    public PacketSendScreenToServer(FriendlyByteBuf buf) {

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
            ServerPlayer player = context.get().getSender();

            NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY.with(() -> player),
                    new PacketSendScreenToTrackingPlayers(player.getUUID(), name));

        });
        context.get().setPacketHandled(true);
    }

    public void encrypt(int id) {

        NetworkHandler.NETWORK.registerMessage(id, PacketSendScreenToServer.class, PacketSendScreenToServer::encode, PacketSendScreenToServer::new,
                PacketSendScreenToServer::handle);

    }

}
