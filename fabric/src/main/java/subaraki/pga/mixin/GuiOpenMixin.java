package subaraki.pga.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import subaraki.pga.capability.FabricScreenData;
import subaraki.pga.event.CommonGuiOpenEvent;
import subaraki.pga.network.SPacketTracking;
import subaraki.pga.util.ClientReferences;

@Mixin(Minecraft.class)
public class GuiOpenMixin {

    @Inject(method = "setScreen", at = @At("TAIL"))
    public void interceptScreen(Screen screen, CallbackInfo ci) {
        if (ClientReferences.getClientPlayer() != null) {
            FabricScreenData.get(ClientReferences.getClientPlayer()).ifPresent(data -> {
                String resultName = CommonGuiOpenEvent.onOpen(data, screen);
                //send own data to others
                ClientPlayNetworking.send(new SPacketTracking(resultName, ClientReferences.getClientPlayer().getUUID()));
            });
        }
    }
}
