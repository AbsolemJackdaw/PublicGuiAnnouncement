package subaraki.pga.mod;

import subaraki.pga.network.ClientNetwork;
import subaraki.pga.network.FabricNetwork;

public class ScreenModClient extends CommonScreenMod implements net.fabricmc.api.ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FabricNetwork.registerClient();
    }
}
