package subaraki.pga.network.server;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import subaraki.pga.network.IPacket;
import subaraki.pga.network.client.ClientNetwork;

import java.util.UUID;

public class SPacketTracking implements IPacket {
    
    private final FriendlyByteBuf buf;
    private final ServerPlayer player; //you
    private final String ref;
    boolean uuidPresent = false;
    public SPacketTracking(ServerPlayer player, @NotNull Integer uuid, String ref) {
        
        this.player = player;
        this.ref = ref;
        buf = PacketByteBufs.create();
        buf.writeUtf(ref, ref.length() + 1);
        uuidPresent = false;
        //get entity id. cannot recontruct player from uuid on client
        buf.writeInt(uuid); //null check has been made by uuipresent
    }
    
    public SPacketTracking(ServerPlayer player, @NotNull UUID uuid, String ref) {
        this.player = player;
        this.ref = ref;
        buf = PacketByteBufs.create();
        buf.writeUtf(ref, ref.length() + 1);
        uuidPresent = true;
        //get entity id. cannot recontruct player from uuid on client
        buf.writeUUID(uuid); //nullc check has been made by uuipresent
    }
    @Override
    public void send() {
        ServerPlayNetworking.send(player, uuidPresent ? ClientNetwork.UPDATE_TRACKING : ClientNetwork.UPDATE_TRACKING_W_ID, buf);
    }
    
}
