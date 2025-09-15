package subaraki.pga.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import subaraki.pga.event.client.OpenGuiEventHandler;

@Mixin(Minecraft.class)
public class GuiOpenMixin {
    
    @Inject(method = "setScreen", at = @At("HEAD"))
    public void interceptScreen(Screen screen, CallbackInfo ci) {
        
        OpenGuiEventHandler.openGuiEvent(screen);
    }
    
}
