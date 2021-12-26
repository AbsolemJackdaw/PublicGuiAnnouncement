package subaraki.pga.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import subaraki.pga.util.ScreenPackReader;

@Mixin(Minecraft.class)
public class ReloadResourcesMixin {
    
    @Shadow
    @Final
    private ReloadableResourceManager resourceManager;
    
    @Inject(method = "<init>", at = @At(value = "INVOKE_STRING", target = "Lcom/mojang/blaze3d/platform/Window;setErrorSection(Ljava/lang/String;)V",
            args = "ldc=Post startup"))
    public void registerReloadListener(GameConfig gameConfig, CallbackInfo ci) {
        /*
         * We aren't using the below as it is too late for model registration.
         * <code> ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(ResourcePackReader.INSTANCE); </code>
         */
        resourceManager.registerReloadListener(new ScreenPackReader());
    }
    
}