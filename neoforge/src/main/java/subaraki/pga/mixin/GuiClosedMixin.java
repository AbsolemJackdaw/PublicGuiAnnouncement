package subaraki.pga.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import subaraki.pga.event.client.OpenGuiEventHandler;

@Mixin(Minecraft.class)
public class GuiClosedMixin {

    @Inject(method = "setScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferUploader;reset()V"))
    public void interceptClose(Screen screen, CallbackInfo ci) {
        //called before the reset buffer, where the screen is set
        if (Minecraft.getInstance().screen == null) //do not check against the screen in the arguments, as the screen can be set by the events
            OpenGuiEventHandler.closeGui();
        else
            OpenGuiEventHandler.openGui(Minecraft.getInstance().screen);

    }
}
