package subaraki.pga.mod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import subaraki.pga.capability.ScreenCapability;
import subaraki.pga.network.NetworkHandler;

@Mod(ScreenMod.MODID)
@EventBusSubscriber(modid = ScreenMod.MODID, bus = Bus.MOD)
public class ScreenMod {

    public static final String MODID = "publicguiannouncement";
    public static final Logger LOG = LogManager.getLogger(MODID);

    public ScreenMod() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

        new ScreenCapability().register();
        new NetworkHandler();
    }

}
