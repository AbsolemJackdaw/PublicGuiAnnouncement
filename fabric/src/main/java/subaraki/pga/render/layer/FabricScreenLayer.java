//package subaraki.pga.render.layer;
//
//import net.minecraft.client.model.PlayerModel;
//import net.minecraft.client.renderer.entity.RenderLayerParent;
//import net.minecraft.client.renderer.entity.state.AvatarRenderState;
//import net.minecraft.world.entity.player.Player;
//import subaraki.pga.capability.FabricScreenData;
//import subaraki.pga.capability.ScreenData;
//
//import java.util.Optional;
//
//public class FabricScreenLayer extends CommonLayer {
//
//    public FabricScreenLayer(RenderLayerParent <AvatarRenderState, PlayerModel> renderer) {
//
//        super(renderer);
//    }
//
//    @Override
//    protected Optional<? extends ScreenData> getDataOptional(Player player) {
//
//        return FabricScreenData.get(player);
//    }
//
//}
