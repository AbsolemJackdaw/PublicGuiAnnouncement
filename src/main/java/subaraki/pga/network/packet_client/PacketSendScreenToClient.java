package subaraki.pga.network.packet_client;

import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
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

    public PacketSendScreenToClient(PacketBuffer buf) {

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

            PlayerEntity player = ClientReferences.getClientPlayer();

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
