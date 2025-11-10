package subaraki.pga.capability;

import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public interface IPGAState {
    void pga$setData(Player avatar);

    Optional<? extends ScreenData> pga$getDataOptional();
}
