package subaraki.pga.event.client;

import net.minecraft.client.gui.screens.Screen;
import subaraki.pga.capability.FabricScreenData;
import subaraki.pga.event.CommonGuiOpenEvent;
import subaraki.pga.network.client.CPacketTracking;
import subaraki.pga.util.ClientReferences;

public class OpenGuiEventHandler {

    public static void openGuiEvent(Screen screen) {

        if (ClientReferences.getClientPlayer() != null) {
            String resultName = CommonGuiOpenEvent.onOpen(
                    FabricScreenData.get(ClientReferences.getClientPlayer()),
                    screen
            );
            //send own data to others
            new CPacketTracking(resultName, ClientReferences.getClientPlayer().getUUID()).send();
        }

    }

}
