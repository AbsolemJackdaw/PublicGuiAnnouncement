package subaraki.pga.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import java.util.concurrent.Callable;

public class ScreenCapability {

    public static Capability<ScreenData> CAPABILITY = CapabilityManager.get(new CapabilityToken<ScreenData>() {
    });

    /*
     * This class handles constructing new instances for this capability
     */
    public static class DefaultInstanceFactory implements Callable<ScreenData> {

        @Override
        public ScreenData call() throws Exception {

            return new ScreenData();
        }
    }
}
