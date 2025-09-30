package subaraki.pga.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record CPacketSelf(String uiScreenRef) implements CustomPacketPayload {
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return CommonChannel.CPACKETSELF_TYPE;
    }
}