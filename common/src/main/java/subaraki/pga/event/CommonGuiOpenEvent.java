package subaraki.pga.event;

import net.minecraft.client.gui.screens.Screen;
import subaraki.pga.capability.ScreenData;

public class CommonGuiOpenEvent {

    public static String onOpen(ScreenData data, Screen screen) {
        // minecraft sets screens twice to null for closing them.
        // no current workaround to reduce packet spam
        String simpleName = screen == null ? ScreenData.CLOSE_SCREEN : screen.getClass().getName();
        data.setClientScreen(simpleName);
        return simpleName;
    }

}
