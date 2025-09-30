package subaraki.pga.network;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import subaraki.pga.mod.CommonScreenMod;

public class CommonChannel {

    private static final ResourceLocation SPACKETSELF = ResourceLocation.fromNamespaceAndPath(CommonScreenMod.MODID, "s_self");
    public static final CustomPacketPayload.Type<SPacketSelf> SPACKETSELF_TYPE = new CustomPacketPayload.Type<>(SPACKETSELF);
    public static StreamCodec<FriendlyByteBuf, SPacketSelf> SPACKETSELF_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SPacketSelf::uiScreenRef,
            SPacketSelf::new);

    private static final ResourceLocation SPACKETTRACKING = ResourceLocation.fromNamespaceAndPath(CommonScreenMod.MODID, "s_tracking");
    public static final CustomPacketPayload.Type<SPacketTracking> SPACKETTRACKING_TYPE = new CustomPacketPayload.Type<>(SPACKETTRACKING);
    public static StreamCodec<FriendlyByteBuf, SPacketTracking> SPACKETTRACKING_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SPacketTracking::uiScreenRef,
            UUIDUtil.STREAM_CODEC,
            SPacketTracking::otherPlayer,
            SPacketTracking::new);

    private static final ResourceLocation CPACKETSELF = ResourceLocation.fromNamespaceAndPath(CommonScreenMod.MODID, "c_self");
    public static final CustomPacketPayload.Type<CPacketSelf> CPACKETSELF_TYPE = new CustomPacketPayload.Type<>(CPACKETSELF);
    public static StreamCodec<FriendlyByteBuf, CPacketSelf> CPACKETSELF_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            CPacketSelf::uiScreenRef,
            CPacketSelf::new
    );

    private static final ResourceLocation CPACKETTRACKING = ResourceLocation.fromNamespaceAndPath(CommonScreenMod.MODID, "c_tracking");
    public static final CustomPacketPayload.Type<CPacketTracking> CPACKETTRACKING_TYPE = new CustomPacketPayload.Type<>(CPACKETTRACKING);
    public static StreamCodec<FriendlyByteBuf, CPacketTracking> CPACKETTRACKING_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            CPacketTracking::uiScreenRef,
            UUIDUtil.STREAM_CODEC,
            CPacketTracking::otherPlayer,
            CPacketTracking::new);
}
