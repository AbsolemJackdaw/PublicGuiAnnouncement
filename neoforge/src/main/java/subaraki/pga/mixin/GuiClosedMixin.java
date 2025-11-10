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

    @Inject(method = "setScreen(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At(value = "TAIL"))
    public void interceptClose(Screen screen, CallbackInfo ci) {
        if (Minecraft.getInstance().screen == null) //do not check against the screen in the arguments, as the screen can be set by the events
            OpenGuiEventHandler.closeGui();
        else
            OpenGuiEventHandler.openGui(Minecraft.getInstance().screen);

    }
}
