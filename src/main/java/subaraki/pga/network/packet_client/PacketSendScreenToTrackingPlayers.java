package subaraki.pga.network.packet_client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.network.IPacketBase;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.util.ClientReferences;

import java.util.UUID;
import java.util.function.Supplier;

public class PacketSendScreenToTrackingPlayers implements IPacketBase {

    private UUID uuid;
    private String name;

    public PacketSendScreenToTrackingPlayers() {

    }

    public PacketSendScreenToTrackingPlayers(FriendlyByteBuf buf) {

        decode(buf);
    }

    public PacketSendScreenToTrackingPlayers(UUID uuid, String name) {

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

            Player distPlayer = ClientReferences.getClientPlayerByUUID(uuid);
            if (distPlayer != null) {
                ScreenData.get(distPlayer).ifPresent(screenData -> screenData.setViewingScreen(name));

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
