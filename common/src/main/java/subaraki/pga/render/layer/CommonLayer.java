package subaraki.pga.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import subaraki.pga.capability.IPGAState;
import subaraki.pga.config.ConfigHandler;
import subaraki.pga.mod.CommonScreenMod;

public class CommonLayer extends RenderLayer<AvatarRenderState, PlayerModel> {

    private static final Identifier CLOUD = Identifier.fromNamespaceAndPath(CommonScreenMod.MODID, "textures/gui/cloud.png");
    private static final float PIXELSCALE = 0.0625F;

    public CommonLayer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack stack, SubmitNodeCollector submitNodeCollector, int lightCoords, AvatarRenderState avatarRenderState, float v, float v1) {

        if (Minecraft.getInstance().screen instanceof InventoryScreen
                || Minecraft.getInstance().screen instanceof CreativeModeInventoryScreen) {
            return;
        }

        if (avatarRenderState instanceof IPGAState state)
            state.pga$getDataOptional().ifPresent(data -> {
                if (data.getClientScreen() != null) {
                    Identifier resLoc = data.lookupResloc();

                    if (resLoc != null) {
                        int gui_size_x = data.getClientScreen().getSizeX();
                        int gui_size_y = data.getClientScreen().getSizeY();

                        int texture_size_x = data.getClientScreen().getTexX();
                        int texture_size_y = data.getClientScreen().getTexY();

                        stack.pushPose();

                        if (ConfigHandler.renderDefault()) {
                            getParentModel().getHead().translateAndRotate(stack);
                        } else {
                            // undo body rotation. render independant of player rotation
                            float f = Mth.rotLerp(avatarRenderState.walkAnimationPos, avatarRenderState.bodyRot, avatarRenderState.bodyRot);
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
                            submitNodeCollector.submitCustomGeometry(stack, RenderTypes.entitySmoothCutout(resLoc), (pose, vertexConsumer) -> renderOnFace(pose, vertexConsumer, sizeX, sizeY, texture_size_x, texture_size_y, translateX, translateY, lightCoords));

                        } else {
                            //move to above the player head, centered and mirrored on head
                            stack.translate(translateX - sizeX * PIXELSCALE, translateY - sizeY * PIXELSCALE, 0);
                            stack.scale(PIXELSCALE, PIXELSCALE, PIXELSCALE);

                            //TODO reimplement
                            if (Minecraft.getInstance().getCameraEntity() != null)
//                                //only rotate to camera if the player isn't the camera itself (won't render then as the rotation is effectively null/NaN)
//                                if (!renderedPlayer.getUUID().equals(Minecraft.getInstance().getCameraEntity().getUUID()))
                                if (!ConfigHandler.bubbleDefault().equals("NONE"))
                                    rotateToCamera(stack, avatarRenderState.x, avatarRenderState.y, avatarRenderState.z);

                            //draw cloud in dead center.
                            //stretch cloud to fit found gui sizes
                            //cloud file has hardcoded size values

                            for (boolean flag : new boolean[]{true, false}) {
                                //render front : true
                                //Render back : false
                                submitNodeCollector.submitCustomGeometry(stack, RenderTypes.entityCutout(CLOUD), (pose, vertex) -> {
                                    renderCloud(pose, vertex, gui_size_x, gui_size_y, lightCoords, flag);
                                });
                                submitNodeCollector.submitCustomGeometry(stack, RenderTypes.entitySmoothCutout(resLoc), (pose, vertex) -> {
                                    renderScreenAroundCloud(pose, vertex, sizeX, sizeY, texture_size_x, texture_size_y, lightCoords, flag);
                                });
                            }
                        }
                        stack.popPose();
                    }
                }
            });
    }

    private void renderCloud(PoseStack.Pose stack, VertexConsumer vertex, int gui_size_x, int gui_size_y, int packedLight, boolean mirrored) {
        float stretchX = gui_size_x / 255f;
        float stretchY = gui_size_y / 255f;
        stack.scale(stretchX, stretchY, 0);
        if (!mirrored) {
            stack.translate(255 * 0.0625f, 0, 0);
        }
        blitRect(stack, vertex, packedLight, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0, 255 * 0.0625f, 255 * 0.0625f, 255, 255, mirrored);
    }

    private void renderScreenAroundCloud(PoseStack.Pose stack, VertexConsumer vertex, float sizeX, float sizeY, int texture_size_x, int texture_size_y, int packedLight, boolean mirrored) {
        //render gui twice : in front and behind cloud
        var offsetZ = 0.02f;
        var offsetX = sizeX / 4f;
        if (mirrored) offsetZ *= -1f;
        else offsetX *= 3f;

        //translate to center of original size
        stack.translate(offsetX, sizeY / 4, offsetZ);
        //Scale by half to fit cloud
        stack.scale(0.5f, 0.5f, 0.5f);
        blitRect(stack, vertex, packedLight, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0, sizeX, sizeY, texture_size_x, texture_size_y, mirrored);
    }

    protected static void blitRect(PoseStack.Pose pose, VertexConsumer builder, int packedLight, int overlay, float x0, float y0, float xt, float yt, float width, float height, int tWidth, int tHeight, boolean mirrored) {

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

        Matrix4f matrix = pose.pose();
        builder.addVertex(matrix, x0, y1, 0.0f).setColor(1.0f, 1.0f, 1.0f, 1.0f).setUv(tx0, ty1).setOverlay(overlay).setLight(packedLight).setNormal(pose, 0, 0, 1);
        builder.addVertex(matrix, x1, y1, 0.0f).setColor(1.0f, 1.0f, 1.0f, 1.0f).setUv(tx1, ty1).setOverlay(overlay).setLight(packedLight).setNormal(pose, 0, 0, 1);
        builder.addVertex(matrix, x1, y0, 0.0f).setColor(1.0f, 1.0f, 1.0f, 1.0f).setUv(tx1, ty0).setOverlay(overlay).setLight(packedLight).setNormal(pose, 0, 0, 1);
        builder.addVertex(matrix, x0, y0, 0.0f).setColor(1.0f, 1.0f, 1.0f, 1.0f).setUv(tx0, ty0).setOverlay(overlay).setLight(packedLight).setNormal(pose, 0, 0, 1);

    }

    private void renderOnFace(PoseStack.Pose stack, VertexConsumer vertex, float sizeX, float sizeY, int texture_size_x, int texture_size_y, float translateX, float translateY, int packedLight) {
        //reguler rendering in front of the face
        stack.translate(translateX, translateY, -0.75f);
        stack.scale(PIXELSCALE, PIXELSCALE, PIXELSCALE);
        blitRect(stack, vertex, packedLight, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0, sizeX, sizeY, texture_size_x, texture_size_y, false);
    }

    private void rotateToCamera(PoseStack stack, double x, double y, double z) {
        float off = 6f;
        stack.translate(off, 0, 0);
        if (ConfigHandler.bubbleDefault().equals("PLAYER")) {
            Vec3 cam = Minecraft.getInstance().getCameraEntity().position();
            Vec3 player = new Vec3(x, y, z);
            float rotY = (float) Math.atan2((cam.x - player.x), (cam.z - player.z));
            stack.mulPose(Axis.YP.rotation(-rotY));
        } else if (ConfigHandler.bubbleDefault().equals("CAMERA")) {
            float rotY = Minecraft.getInstance().getCameraEntity().getYRot();
            stack.mulPose(Axis.YP.rotationDegrees(rotY));
        }
        stack.translate(-off, 0, 0);
    }
}