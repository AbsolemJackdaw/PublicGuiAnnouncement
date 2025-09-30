package subaraki.pga.capability;

import net.minecraft.resources.ResourceLocation;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import subaraki.pga.mod.CommonScreenMod;

public class Components implements EntityComponentInitializer {

    // retrieving a type for my component or for a required dependency's
    public static final ComponentKey<FabricScreenData> DATA = ComponentRegistry.getOrCreate(ResourceLocation.fromNamespaceAndPath(CommonScreenMod.MODID, "screendata"), FabricScreenData.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {

        registry.registerForPlayers(DATA, FabricScreenData::new, RespawnCopyStrategy.NEVER_COPY);
    }

}
