package subaraki.pga.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import subaraki.pga.mod.CommonScreenMod;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.util.ClientReferences;

@EventBusSubscriber(modid = CommonScreenMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";

    @SubscribeEvent // on the mod event bus
    public static void register(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar NETWORK = event.registrar(PROTOCOL_VERSION);
        NETWORK.playToServer(CommonChannel.SPACKETSELF_TYPE, CommonChannel.SPACKETSELF_CODEC, PayLoadHandler::handleSSelf);
        NETWORK.playToServer(CommonChannel.SPACKETTRACKING_TYPE, CommonChannel.SPACKETTRACKING_CODEC, PayLoadHandler::handleSTracking);
        NETWORK.playToClient(CommonChannel.CPACKETSELF_TYPE, CommonChannel.CPACKETSELF_CODEC, PayLoadHandler::handleCSelf);
        NETWORK.playToClient(CommonChannel.CPACKETTRACKING_TYPE, CommonChannel.CPACKETTRACKING_CODEC, PayLoadHandler::handleCTracking);
    }

    private static class PayLoadHandler {
        private static void handleSSelf(final SPacketSelf packet, final IPayloadContext context) {
            context.enqueueWork(() -> {
                context.player().getData(ScreenMod.PGA_DATA).setServerData(packet.uiScreenRef());
            });
        }

        private static void handleSTracking(final SPacketTracking packet, final IPayloadContext context) {
            context.enqueueWork(() -> {
                PacketDistributor.sendToPlayersTrackingEntity(context.player(), new CPacketTracking(packet.uiScreenRef(), packet.otherPlayer()));
            });
        }

        private static void handleCSelf(final CPacketSelf packet, final IPayloadContext context) {
            context.enqueueWork(() -> {
                context.player().getData(ScreenMod.PGA_DATA).setClientScreen(packet.uiScreenRef());
            });
        }

        private static void handleCTracking(final CPacketTracking packet, final IPayloadContext context) {
            context.enqueueWork(() -> {
                var otherPlayer = ClientReferences.getClientPlayerByUUID(packet.otherPlayer());
                if (otherPlayer != null)
                    otherPlayer.getData(ScreenMod.PGA_DATA).setClientScreen(packet.uiScreenRef());
            });
        }
    }
}
