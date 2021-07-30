package subaraki.pga.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import subaraki.pga.capability.CapabilityProvider;

public class AttachEventHandler {

    @SubscribeEvent
    public void onAttach(AttachCapabilitiesEvent<Entity> event) {

        final Object entity = event.getObject();

        if (entity instanceof Player)
            event.addCapability(CapabilityProvider.KEY, new CapabilityProvider((Player) entity));
    }
}
