package subaraki.pga.mod;

import net.fabricmc.api.ModInitializer;
import subaraki.pga.network.FabricNetwork;
import subaraki.pga.network.ServerNetwork;

public class ScreenMod extends CommonScreenMod implements ModInitializer {

    @Override
    public void onInitialize() {
        FabricNetwork.registerPackets();
        FabricNetwork.registerServer();
    }
}


