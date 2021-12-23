package subaraki.pga.mod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ScreenMod.MODID)
@EventBusSubscriber(modid = ScreenMod.MODID, bus = Bus.MOD)
public class ScreenMod {

    public static final String MODID = "publicguiannouncement";
    public static final Logger LOG = LogManager.getLogger(MODID);

    public ScreenMod() {
    }
}
