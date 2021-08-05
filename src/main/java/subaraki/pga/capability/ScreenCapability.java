package subaraki.pga.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class ScreenCapability {

    @CapabilityInject(ScreenData.class)
    public static Capability<ScreenData> CAPABILITY;

    /*
     * This registers our capability to the manager
     */
    public void register() {

        CapabilityManager.INSTANCE.register(

                // This is the class the capability works with
                ScreenData.class
        );
    }

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
