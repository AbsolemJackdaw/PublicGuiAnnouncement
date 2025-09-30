package subaraki.pga.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.render.layer.CommonLayer;

import java.util.Optional;

public class NeoScreenLayer<T extends LivingEntity, M extends EntityModel<T>> extends CommonLayer<T, M> {

    public NeoScreenLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    protected Optional<? extends ScreenData> getDataOptional(Player player) {

        return player == null ? Optional.empty() : Optional.of(player.getData(ScreenMod.PGA_DATA));
    }

}
