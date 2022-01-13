package subaraki.pga.network.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import subaraki.pga.network.CommonChannel;
import subaraki.pga.network.IPacket;

public class CPacketSelf implements IPacket {
    FriendlyByteBuf buf;

    public CPacketSelf(String ref) {
        buf = PacketByteBufs.create();
        buf.writeByte(CommonChannel.SPACKETSELF);//forge network compat
        buf.writeUtf(ref, 128);
    }

    @Override
    public void send() {
        ClientPlayNetworking.send(CommonChannel.CHANNEL, buf);
    }
}
