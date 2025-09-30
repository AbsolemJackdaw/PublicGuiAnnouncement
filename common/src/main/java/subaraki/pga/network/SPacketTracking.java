package subaraki.pga.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

public record SPacketTracking(String uiScreenRef, UUID otherPlayer) implements CustomPacketPayload{
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return CommonChannel.SPACKETTRACKING_TYPE;
    }
}