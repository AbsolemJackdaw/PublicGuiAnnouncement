package subaraki.pga.network.server;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import subaraki.pga.network.IPacket;
import subaraki.pga.network.CommonChannel;

public class SPacketSelf implements IPacket {
    FriendlyByteBuf buf;
    ServerPlayer player;

    public SPacketSelf(ServerPlayer player, String ref) {

        this.player = player;
        buf = PacketByteBufs.create();
        buf.writeByte(CommonChannel.CPACKETSELF); //forge network compat
        buf.writeUtf(ref, 128);
    }

    @Override
    public void send() {
        ServerPlayNetworking.send(player, CommonChannel.CHANNEL, buf);
    }
}
