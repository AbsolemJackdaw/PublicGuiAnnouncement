package subaraki.pga.network.packet_client;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.network.IPacketBase;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.util.ClientReferences;

public class PacketSendScreenToClient implements IPacketBase {

    public PacketSendScreenToClient() {

    }

    private String name;

    public PacketSendScreenToClient(String name) {

        this.name = name;
    }

    public PacketSendScreenToClient(FriendlyByteBuf buf) {

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

            Player player = ClientReferences.getClientPlayer();

            ScreenData.get(player).ifPresent(t -> t.setViewingScreen(name));

        });
        context.get().setPacketHandled(true);

    }

    @Override
    public void encrypt(int id) {

        NetworkHandler.NETWORK.registerMessage(id, PacketSendScreenToClient.class, PacketSendScreenToClient::encode, PacketSendScreenToClient::new,
                PacketSendScreenToClient::handle);
    }

}
