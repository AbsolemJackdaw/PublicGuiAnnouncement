package subaraki.pga.capability;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;
import subaraki.pga.capability.FabricScreenData;
import subaraki.pga.mod.CommonScreenMod;

public class Components implements EntityComponentInitializer {
    
    // retrieving a type for my component or for a required dependency's
    public static final ComponentKey<FabricScreenData> DATA = ComponentRegistry.getOrCreate(new ResourceLocation(CommonScreenMod.MODID, "screendata"), FabricScreenData.class);
    
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        
        registry.registerForPlayers(DATA, FabricScreenData::new, RespawnCopyStrategy.NEVER_COPY);
    }
    
}
