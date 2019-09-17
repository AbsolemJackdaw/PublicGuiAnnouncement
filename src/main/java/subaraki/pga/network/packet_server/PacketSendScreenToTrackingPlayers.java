package subaraki.pga.network.packet_server;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.network.IPacketBase;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.util.ClientReferences;

public class PacketSendScreenToTrackingPlayers implements IPacketBase {

    private UUID uuid;
    private String name;

    public PacketSendScreenToTrackingPlayers() {

    }

    public PacketSendScreenToTrackingPlayers(PacketBuffer buf) {

        decode(buf);
    }

    public PacketSendScreenToTrackingPlayers(UUID uuid, String name) {

        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public void encode(PacketBuffer buf) {

        buf.writeString(name);
        buf.writeUniqueId(uuid);
    }

    @Override
    public void decode(PacketBuffer buf) {

        name = buf.readString();
        uuid = buf.readUniqueId();
    }

    @Override
    public void handle(Supplier<Context> context) {

        context.get().enqueueWork(() -> {

            PlayerEntity distPlayer = ClientReferences.getClientPlayerByUUID(uuid);
            if (distPlayer != null) {
                ScreenData.get(distPlayer).ifPresent(t -> t.setViewingScreen(name));

            }
        });
        context.get().setPacketHandled(true);
    }

    @Override
    public void encrypt(int id) {

        NetworkHandler.NETWORK.registerMessage(id, PacketSendScreenToTrackingPlayers.class, PacketSendScreenToTrackingPlayers::encode,
                PacketSendScreenToTrackingPlayers::new, PacketSendScreenToTrackingPlayers::handle);
    }

}
