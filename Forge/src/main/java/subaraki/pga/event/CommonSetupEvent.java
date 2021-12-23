package subaraki.pga.event;

import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = ScreenMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetupEvent {

    @SubscribeEvent
    public static void startCommonSetup(FMLCommonSetupEvent event) {
        NetworkHandler.register();
    }

    @SubscribeEvent
    public static void capRegistry(RegisterCapabilitiesEvent event) {
        event.register(ScreenData.class);
    }
}
