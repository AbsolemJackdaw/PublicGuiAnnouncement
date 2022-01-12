package subaraki.pga.network;

import net.minecraft.resources.ResourceLocation;
import subaraki.pga.mod.CommonScreenMod;

public class CommonChannel {
    public static final ResourceLocation CHANNEL = new ResourceLocation(CommonScreenMod.MODID, "pga_network");
    public static final byte SPACKETSELF = 0;
    public static final byte SPACKETTRACKING = 1;
    public static final byte CPACKETSELF = 2;
    public static final byte CPACKETTRACKING = 3;
}
