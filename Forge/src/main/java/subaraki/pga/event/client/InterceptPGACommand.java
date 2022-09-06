package subaraki.pga.event.client;

import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.util.ClientReferences;
import subaraki.pga.util.ScreenPackReader;

@Mod.EventBusSubscriber(modid = ScreenMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InterceptPGACommand {

    @SubscribeEvent
    public static void getText(ClientChatEvent event) {
        if (event.getOriginalMessage().startsWith("pga") && event.getOriginalMessage().endsWith(" print")) {
            event.setCanceled(true);
            ScreenPackReader.printMissing();
            ClientReferences.getClientPlayer().displayClientMessage(Component.literal("printed missing screens to latest.log"), false);
        }
    }
}
