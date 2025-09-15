package subaraki.pga.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import subaraki.pga.capability.ScreenCapability;
import subaraki.pga.mod.CommonScreenMod;

@Mod.EventBusSubscriber(modid = CommonScreenMod.MODID, bus = Bus.FORGE)
public class AttachEventHandler {

    @SubscribeEvent
    public static void onAttach(AttachCapabilitiesEvent<Entity> event) {

        final Object entity = event.getObject();

        if (entity instanceof Player)
            event.addCapability(ScreenCapability.KEY, new ScreenCapability((Player) entity));
    }
}
