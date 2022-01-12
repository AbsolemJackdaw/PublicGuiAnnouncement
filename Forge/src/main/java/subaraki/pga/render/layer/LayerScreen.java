package subaraki.pga.render.layer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import subaraki.pga.capability.ForgeScreenData;
import subaraki.pga.capability.ScreenData;

import java.util.Optional;

public class LayerScreen<T extends LivingEntity, M extends EntityModel<T>> extends CommonLayer<T, M> {

    public LayerScreen(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    protected Optional<? extends ScreenData> getDataOptional(Player player) {

        return ForgeScreenData.get(player).resolve();
    }

}
