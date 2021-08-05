package subaraki.pga.network.packet_client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.network.IPacketBase;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_server.PacketSendScreenToServer;
import subaraki.pga.util.ClientReferences;

import java.util.function.Supplier;

public class PacketGetScreenFromClient implements IPacketBase {

    public PacketGetScreenFromClient() {

    }

    public PacketGetScreenFromClient(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {

    }

    @Override
    public void decode(FriendlyByteBuf buf) {

    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {

        context.get().enqueueWork(() -> {
            Player player = ClientReferences.getClientPlayer();
            ScreenData.get(player).ifPresent(screenData -> {
                if (screenData.getViewingScreen() != null)
                    NetworkHandler.NETWORK.sendToServer(new PacketSendScreenToServer(screenData.getViewingScreen().getRefName()));
            });

        });

        context.get().setPacketHandled(true);
    }

    @Override
    public void encrypt(int id) {
        NetworkHandler.NETWORK.registerMessage(id, PacketGetScreenFromClient.class, PacketGetScreenFromClient::encode,
                PacketGetScreenFromClient::new, PacketGetScreenFromClient::handle);
    }
}
