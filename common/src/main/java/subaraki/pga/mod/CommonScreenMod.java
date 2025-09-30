package subaraki.pga.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import subaraki.pga.capability.ScreenData;

import java.util.Optional;

public class CommonScreenMod {

    public static final String MODID = "publicguiannouncement";
    public static final Logger LOG = LogManager.getLogger(MODID);
    public static Optional<? extends ScreenData> PLAYERDATA;
}
