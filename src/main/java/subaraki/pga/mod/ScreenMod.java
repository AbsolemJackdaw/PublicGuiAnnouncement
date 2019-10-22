package subaraki.pga.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import subaraki.pga.capability.ScreenCapability;
import subaraki.pga.event.EventRegistry;
import subaraki.pga.event.client.OpenGuiEventHandler;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.util.ClientReferences;
import subaraki.pga.util.ScreenPackReader;

@Mod(ScreenMod.MODID)
@EventBusSubscriber(modid = ScreenMod.MODID, bus = Bus.MOD)
public class ScreenMod {

    public static final String MODID = "publicguiannouncement";
    public static final Logger LOG = LogManager.getLogger(MODID);

    public ScreenMod() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        new ScreenCapability().register();
        new EventRegistry();
        new NetworkHandler();
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        new OpenGuiEventHandler();

        new ScreenPackReader().registerReloadListener().init();

        ClientReferences.loadLayers();
    }
}
