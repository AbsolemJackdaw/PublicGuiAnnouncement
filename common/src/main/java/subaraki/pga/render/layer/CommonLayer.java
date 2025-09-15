package subaraki.pga.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.AxisAngle4f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import subaraki.pga.capability.ScreenData;
import subaraki.pga.config.ConfigHandler;
import subaraki.pga.mod.CommonScreenMod;

import java.util.Optional;

public class CommonLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation CLOUD = new ResourceLocation(CommonScreenMod.MODID, "textures/gui/cloud.png");
    private static final float PIXELSCALE = 0.0625F;

    public CommonLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    protected static void blitRect(PoseStack matrixStack, VertexConsumer builder, int packedLight, int overlay, float x0, float y0, float xt, float yt, float width, float height, int tWidth, int tHeight, boolean mirrored) {

        float pixelScale = 0.0625f;
        float tx0 = xt / (tWidth * pixelScale);
        float ty0 = yt / (tHeight * pixelScale);
        float tx1 = tx0 + width / (tWidth * pixelScale);
        float ty1 = ty0 + height / (tHeight * pixelScale);

        float x1 = x0 - width;
        float y1 = y0 + height;

        if (mirrored) {
            x1 *= -1;
        }

        Matrix4f matrix = matrixStack.last().pose();
        Matrix3f normal = matrixStack.last().normal();

        builder.vertex(matrix, x0, y1, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx0, ty1).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();
        builder.vertex(matrix, x1, y1, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx1, ty1).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();
        builder.vertex(matrix, x1, y0, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx1, ty0).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();
        builder.vertex(matrix, x0, y0, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx0, ty0).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();

    }


    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing,
                       float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (Minecraft.getInstance().screen instanceof InventoryScreen
                || Minecraft.getInstance().screen instanceof CreativeModeInventoryScreen) {
            return;
        }
        if (!(entity instanceof Player renderedPlayer))
            return;

        getDataOptional(renderedPlayer).ifPresent(data -> {
            if (data.getClientScreen() != null) {
                ResourceLocation resLoc = data.lookupResloc();

                if (resLoc != null) {
                    int gui_size_x = data.getClientScreen().getSizeX();
                    int gui_size_y = data.getClientScreen().getSizeY();

                    int texture_size_x = data.getClientScreen().getTexX();
                    int texture_size_y = data.getClientScreen().getTexY();

                    stack.pushPose();
                    if (ConfigHandler.renderDefault()) {
                        if (getParentModel() instanceof HeadedModel headedModel)
                            headedModel.getHead().translateAndRotate(stack);
                    } else {
                        //undo body rotation. render independant of player rotation
                        float f = Mth.rotLerp(limbSwingAmount, renderedPlayer.yBodyRotO, renderedPlayer.yBodyRot);
                        stack.mulPose(Axis.YP.rotationDegrees(180.0F - f));
                    }


                    float headToCenterOffset = PIXELSCALE * 4;
                    float centerX = (gui_size_x / 2.0F) * PIXELSCALE;
                    float centerY = (gui_size_y / 2.0F) * PIXELSCALE;
                    float sizeX = gui_size_x * PIXELSCALE;
                    float sizeY = gui_size_y * PIXELSCALE;
                    float translateX = centerX * PIXELSCALE;
                    float translateY = -centerY * PIXELSCALE - headToCenterOffset;

                    if (ConfigHandler.renderDefault()) {
                        renderOnFace(stack, buffer, resLoc, sizeX, sizeY, texture_size_x, texture_size_y, translateX, translateY, packedLight);
                    } else {
                        //move to above the player head, centered and mirrored on head
                        stack.translate(translateX - sizeX * PIXELSCALE, translateY - sizeY * PIXELSCALE, 0);
                        stack.scale(PIXELSCALE, PIXELSCALE, PIXELSCALE);


                        if (Minecraft.getInstance().cameraEntity != null)
                            //only rotate to camera if the player isn't the camera itself (wont render then as the rotation is effectively null/NaN)
                            if (!renderedPlayer.getUUID().equals(Minecraft.getInstance().cameraEntity.getUUID()))
                                if (!ConfigHandler.bubbleDefault().equals("NONE"))
                                    rotateToCamera(stack, renderedPlayer);

                        //draw cloud in dead center.
                        //stretch cloud to fit found gui sizes
                        //cloud file has hardcoded size values

                        renderCloud(stack, renderedPlayer, buffer, gui_size_x, gui_size_y, packedLight);
                        renderScreenAroundCloud(stack, renderedPlayer, buffer, resLoc, sizeX, sizeY, texture_size_x, texture_size_y, packedLight);

                    }
                    stack.popPose();
                }
            }
        });
    }

    protected Optional<? extends ScreenData> getDataOptional(Player player) {

        return Optional.empty();
    }

    private void renderCloud(PoseStack stack, Player player, MultiBufferSource buffer, int gui_size_x, int gui_size_y, int packedLight) {
        stack.pushPose();
        float stretchX = gui_size_x / 255f;
        float stretchY = gui_size_y / 255f;
        stack.scale(stretchX, stretchY, 0);
        VertexConsumer builder = buffer.getBuffer(RenderType.entitySmoothCutout(CLOUD));
        blitRect(stack, builder, packedLight, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0, 255 * 0.0625f, 255 * 0.0625f, 255, 255, true);
        stack.popPose();
    }

    private void renderScreenAroundCloud(PoseStack stack, Player player, MultiBufferSource buffer, ResourceLocation guiTex, float sizeX, float sizeY, int texture_size_x, int texture_size_y, int packedLight) {
        //render gui twice : in front and behind cloud
        for (double i = -0.1; i <= 0.1; i += 0.2) {
            stack.pushPose();
            //transale to center of original size
            stack.translate(sizeX / 4f, sizeY / 4f, i);
            //Scale by half to fit cloud
            stack.scale(0.5f, 0.5f, 0.5f);
            VertexConsumer builder = buffer.getBuffer(RenderType.entitySmoothCutout(guiTex));
            blitRect(stack, builder, packedLight, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0, sizeX, sizeY, texture_size_x, texture_size_y, true);
            stack.popPose();
        }
    }

    private void renderOnFace(PoseStack stack, MultiBufferSource buffer, ResourceLocation guiTex, float sizeX, float sizeY, int texture_size_x, int texture_size_y, float translateX, float translateY, int packedLight) {
        //reguler rendering in front of the face
        stack.translate(translateX, translateY, -0.75f);
        stack.scale(PIXELSCALE, PIXELSCALE, PIXELSCALE);
        VertexConsumer builder = buffer.getBuffer(RenderType.entitySmoothCutout(guiTex));
        blitRect(stack, builder, packedLight, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0, sizeX, sizeY, texture_size_x, texture_size_y, false);
    }

    private void rotateToCamera(PoseStack stack, Player player) {
        float off = 6f;
        stack.translate(off, 0, 0);

        if (ConfigHandler.bubbleDefault().equals("PLAYER")) {
            Vec3 cam = Minecraft.getInstance().cameraEntity.position();
            Vec3 play = player.position();
            float rotY = (float) Math.atan2((cam.x - play.x), (cam.z - play.z));
            stack.mulPose(Axis.YP.rotation(-rotY));
        } else if (ConfigHandler.bubbleDefault().equals("CAMERA")) {
            float rotY = Minecraft.getInstance().cameraEntity.getYRot();
            stack.mulPose(Axis.YP.rotationDegrees(rotY));
        }
        stack.translate(-off, 0, 0);
    }
}