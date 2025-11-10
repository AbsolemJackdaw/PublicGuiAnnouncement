package subaraki.pga.mixin;

import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import subaraki.pga.capability.Components;
import subaraki.pga.capability.FabricScreenData;
import subaraki.pga.capability.IPGAState;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.mod.ScreenMod;

import java.util.Optional;

@Mixin(AvatarRenderState.class)
public class MixinPlayerRenderState implements IPGAState {

    @Unique
    private Optional<? extends ScreenData> pga$Data = Optional.empty();

    @Override
    public void pga$setData(Player avatar) {
        try {
            pga$Data = FabricScreenData.get(avatar);
        } catch (NullPointerException e) {
            System.err.println(e);
            System.err.println("tried to set player data of a null player");
        }
    }

    @Override
    public Optional<? extends ScreenData> pga$getDataOptional() {
        return Optional.empty();
    }
}
