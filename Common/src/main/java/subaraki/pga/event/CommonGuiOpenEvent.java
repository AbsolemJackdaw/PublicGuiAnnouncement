package subaraki.pga.event;

import net.minecraft.client.gui.screens.Screen;
import subaraki.pga.capability.ScreenData;

import java.util.Optional;

public class CommonGuiOpenEvent {

    public static String onOpen(Optional<? extends ScreenData> optional, Screen screen) {
        // minecraft sets screens twice to null for closing them.
        // no current workaround to reduce packet spam
        String simpleName = screen == null ? ScreenData.CLOSE_SCREEN : screen.getClass().getName();
        optional.ifPresent(screenData -> {
            screenData.setClientScreen(simpleName);
        });
        return simpleName;
    }

}
