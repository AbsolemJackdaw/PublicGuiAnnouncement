package subaraki.pga.capability;

import java.util.concurrent.Callable;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

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
//                ,
//
//                // This is a helper for users to save and load
//                new StorageHelper(),
//
//                // This is a factory for default instances
//                new DefaultInstanceFactory()
                );
    }

    // /*
    // * This class handles saving and loading the data.
    // */
    // public static class StorageHelper implements Capability.IStorage<ScreenData>
    // {
    //
    // @Override
    // public Tag writeNBT(Capability<ScreenData> capability, ScreenData instance,
    // Direction side) {
    //
    // return new CompoundTag(); // instance.writeData();
    // }
    //
    // @Override
    // public void readNBT(Capability<ScreenData> capability, ScreenData instance,
    // Direction side, Tag nbt) {
    //
    // // instance.readData(nbt);
    // }
    // }

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
