package subaraki.pga.mod;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import subaraki.pga.capability.ScreenData;

public final class Components implements EntityComponentInitializer {
    
    // retrieving a type for my component or for a required dependency's
    public static final ComponentKey<ScreenData> DATA = ComponentRegistry.getOrCreate(new ResourceLocation(ScreenMod.MODID, "screendata"), ScreenData.class);
    
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(DATA, ScreenData::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
