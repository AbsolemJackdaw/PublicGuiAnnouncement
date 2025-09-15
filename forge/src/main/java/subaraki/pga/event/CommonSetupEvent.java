package subaraki.pga.event;

import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import subaraki.pga.capability.ForgeScreenData;
import subaraki.pga.mod.CommonScreenMod;
import subaraki.pga.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = CommonScreenMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetupEvent {

    @SubscribeEvent
    public static void startCommonSetup(FMLCommonSetupEvent event) {
        NetworkHandler.register();
    }

    @SubscribeEvent
    public static void capRegistry(RegisterCapabilitiesEvent event) {
        event.register(ForgeScreenData.class);
    }
}
