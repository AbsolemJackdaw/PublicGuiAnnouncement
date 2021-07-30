package subaraki.pga.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import subaraki.pga.capability.ScreenData;

public class LayerScreen<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    public LayerScreen(RenderLayerParent<T, M> renderer) {

        super(renderer);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float limbSwing,
                       float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (Minecraft.getInstance().screen instanceof InventoryScreen
                || Minecraft.getInstance().screen instanceof CreativeModeInventoryScreen)
            return;

        if (entityIn instanceof Player) {
            Player player = (Player) entityIn;

            ScreenData.get(player).ifPresent(data -> {
                if (data != null && data.getViewingScreen() != null) {

                    ResourceLocation resLoc = data.lookupResloc(data.getViewingScreen().getRefName());

                    if (resLoc != null) {

                        float pixelScale = 0.0625F;
                        int gui_size_x = data.getViewingScreen().getSizeX();
                        int gui_size_y = data.getViewingScreen().getSizeY();

                        int texture_size_x = data.getViewingScreen().getTexX();
                        int texture_size_y = data.getViewingScreen().getTexY();

                        if (getParentModel() instanceof PlayerModel) {
                            PlayerModel<T> playerModel = (PlayerModel<T>) getParentModel();

                            stack.pushPose();
                            // thanks to Its_Meow from the MMD server for this rotation aid !
                            stack.translate(playerModel.head.x / 16.0F, playerModel.head.y / 16.0F,
                                    playerModel.head.z / 16.0F);
                            stack.mulPose(Vector3f.ZP.rotation(playerModel.head.zRot));
                            stack.mulPose(Vector3f.YP.rotation(playerModel.head.yRot));
                            stack.mulPose(Vector3f.XP.rotation(playerModel.head.xRot));

                            float headToCenterOffset = pixelScale * 4;
                            float centerX = (gui_size_x / 2.0F) * pixelScale;
                            float centerY = (gui_size_y / 2.0F) * pixelScale;
                            float sizeX = gui_size_x * pixelScale;
                            float sizeY = gui_size_y * pixelScale;
                            float translateX = -centerX * pixelScale;
                            float translateY = -centerY * pixelScale - headToCenterOffset;

                            stack.translate(translateX, translateY, -pixelScale * 16);

                            stack.scale(pixelScale, pixelScale, pixelScale);

                            VertexConsumer builder = bufferIn.getBuffer(RenderType.entitySmoothCutout(resLoc));
                            blitRect(stack, builder, 0x00F000F0, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0, sizeX, sizeY, texture_size_x, texture_size_y, false);
                            stack.popPose();
                        }

                    }
                }
            });
        }
    }

    private static void blitRect(PoseStack matrixStack, VertexConsumer builder, int packedLight, int overlay, float x0, float y0, float xt, float yt, float width, float height, int tWidth, int tHeight, boolean twoSided) {
        float pixelScale = 0.0625f;
        float tx0 = xt / (tWidth * pixelScale);
        float ty0 = yt / (tHeight * pixelScale);
        float tx1 = tx0 + width / (tWidth * pixelScale);
        float ty1 = ty0 + height / (tHeight * pixelScale);

        float x1 = x0 + width;
        float y1 = y0 + height;

        Matrix4f matrix = matrixStack.last().pose();
        Matrix3f normal = matrixStack.last().normal();
        builder.vertex(matrix, x0, y1, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx0, ty1).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();
        builder.vertex(matrix, x1, y1, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx1, ty1).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();
        builder.vertex(matrix, x1, y0, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx1, ty0).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();
        builder.vertex(matrix, x0, y0, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx0, ty0).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();

        if (twoSided) {
            builder.vertex(matrix, x1, y1, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx1, ty1).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, -1).endVertex();
            builder.vertex(matrix, x0, y1, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx0, ty1).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, -1).endVertex();
            builder.vertex(matrix, x0, y0, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx0, ty0).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, -1).endVertex();
            builder.vertex(matrix, x1, y0, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx1, ty0).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, -1).endVertex();
        }
    }

}
