package subaraki.pga.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

public record CPacketTracking(String uiScreenRef, UUID otherPlayer) implements CustomPacketPayload{
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return CommonChannel.CPACKETTRACKING_TYPE;
    }
}
