package subaraki.pga.event.client;

import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.util.ClientReferences;
import subaraki.pga.util.ScreenPackReader;

@EventBusSubscriber(modid = ScreenMod.MODID, value = Dist.CLIENT)
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
