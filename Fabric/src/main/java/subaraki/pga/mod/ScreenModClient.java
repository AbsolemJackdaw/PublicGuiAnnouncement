package subaraki.pga.mod;

import subaraki.pga.network.client.ClientNetwork;

public class ScreenModClient implements net.fabricmc.api.ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        
        ClientNetwork.register();
    }
    
}
