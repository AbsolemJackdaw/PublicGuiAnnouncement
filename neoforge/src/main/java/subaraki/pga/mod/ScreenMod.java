package subaraki.pga.mod;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import subaraki.pga.capability.ScreenData;

import java.util.function.Supplier;

@Mod(CommonScreenMod.MODID)
public class ScreenMod extends CommonScreenMod {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, CommonScreenMod.MODID);
    // No serialization
    public static final Supplier<AttachmentType<ScreenData>> PGA_DATA = ATTACHMENT_TYPES.register(
            "pga_data", () -> AttachmentType.builder(ScreenData::new).build()
    );

    public ScreenMod(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
