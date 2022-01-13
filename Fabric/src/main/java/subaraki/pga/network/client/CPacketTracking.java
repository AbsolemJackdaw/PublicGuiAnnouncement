package subaraki.pga.network.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import subaraki.pga.network.CommonChannel;
import subaraki.pga.network.IPacket;

import java.util.UUID;

public class CPacketTracking implements IPacket {
    FriendlyByteBuf buf;

    public CPacketTracking(String ref, UUID uuid) {
        buf = PacketByteBufs.create();
        buf.writeByte(CommonChannel.SPACKETTRACKING); //forge network compat
        buf.writeUtf(ref, 128);
        buf.writeUUID(uuid);
    }

    @Override
    public void send() {
        ClientPlayNetworking.send(CommonChannel.CHANNEL, buf);
    }
}
