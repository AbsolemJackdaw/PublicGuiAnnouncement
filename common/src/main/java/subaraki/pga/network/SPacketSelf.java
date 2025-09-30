package subaraki.pga.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SPacketSelf(String uiScreenRef) implements CustomPacketPayload{
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return CommonChannel.SPACKETSELF_TYPE;
    }
}