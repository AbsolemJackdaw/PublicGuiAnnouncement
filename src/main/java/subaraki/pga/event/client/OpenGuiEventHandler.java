package subaraki.pga.event.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_server.PacketSendScreenToServer;

public class OpenGuiEventHandler {

    public OpenGuiEventHandler() {

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void openGuiEvent(GuiOpenEvent event) {

        if (event.getGui() == null) {

            if (Minecraft.getInstance() != null) {

                if (Minecraft.getInstance().player != null) {

                    PlayerEntity player = Minecraft.getInstance().player;

                    ScreenData data = ScreenData.get(player);

                    if (data != null) {
                        data.setViewingScreen(ScreenData.CLOSE_SCREEN);
                        // minecraft sets screens twice to null for closing them. no current workaround
                        // to reduce packet spam
                        NetworkHandler.NETWORK.sendToServer(new PacketSendScreenToServer(ScreenData.CLOSE_SCREEN));
                    }
                }
            }
        }

        if (event.getGui() != null) {

            String simpleName = event.getGui().getClass().getSimpleName();

            if (Minecraft.getInstance() != null) {

                PlayerEntity player = Minecraft.getInstance().player;

                if (player != null) {
                    ScreenData data = ScreenData.get(player);

                    if (data != null) {

                        data.setViewingScreen(simpleName);

                        NetworkHandler.NETWORK.sendToServer(new PacketSendScreenToServer(simpleName));
                    }
                }
            }
        }
    }
}
