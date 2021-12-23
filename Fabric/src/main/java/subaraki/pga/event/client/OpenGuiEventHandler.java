package subaraki.pga.event.client;

import net.minecraft.client.gui.screens.Screen;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.mod.Components;
import subaraki.pga.util.ClientReferences;

import java.util.Optional;

public class OpenGuiEventHandler {
    
    public static void openGuiEvent(Screen screen) {
        
        // minecraft sets screens twice to null for closing them.
        // no current workaround to reduce packet spam
        if(ClientReferences.getClientPlayer() != null) {
            String simpleName = screen == null ? ScreenData.CLOSE_SCREEN : screen.getClass().getName();
            Components.DATA.maybeGet(ClientReferences.getClientPlayer()).ifPresent(screenData -> {
                screenData.setViewingScreen(simpleName);
                Components.DATA.sync(ClientReferences.getClientPlayer());
            });
        }
    }
    
}
