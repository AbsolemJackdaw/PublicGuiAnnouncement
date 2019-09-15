package subaraki.pga.event;

import net.minecraftforge.common.MinecraftForge;

public class EventRegistry {

    public EventRegistry() {

        MinecraftForge.EVENT_BUS.register(new ContainerEventHandler());
        MinecraftForge.EVENT_BUS.register(new AttachEventHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
    }
}