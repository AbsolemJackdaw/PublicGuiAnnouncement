package subaraki.pga.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import subaraki.pga.capability.CapabilityProvider;

public class AttachEventHandler {

    @SubscribeEvent
    public void onAttach(AttachCapabilitiesEvent<Entity> event) {

        final Object entity = event.getObject();

        if (entity instanceof PlayerEntity)
            event.addCapability(CapabilityProvider.KEY, new CapabilityProvider((PlayerEntity) entity));
    }
}
