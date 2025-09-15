package subaraki.pga.mod;

import net.fabricmc.api.ModInitializer;
import subaraki.pga.network.server.ServerNetwork;

public class ScreenMod extends CommonScreenMod implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerNetwork.register();
    }
}


