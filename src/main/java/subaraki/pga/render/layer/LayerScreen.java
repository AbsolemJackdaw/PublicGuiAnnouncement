package subaraki.pga.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import subaraki.pga.capability.ScreenData;

public class LayerScreen extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    private ModelScreen model;

    public LayerScreen(PlayerRenderer renderer) {

        super(renderer);
    }

    @Override
    public void render(MatrixStack mat, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractClientPlayerEntity entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {

        if (Minecraft.getInstance().currentScreen instanceof InventoryScreen || Minecraft.getInstance().currentScreen instanceof CreativeScreen)
            return;

        ScreenData.get(entityIn).ifPresent(data -> {

            if (data != null && data.getViewingScreen() != null)
            {

                ResourceLocation resLoc = data.lookupResloc(data.getViewingScreen().getRefName());

                if (resLoc != null)
                {

                    float pixelScale = 0.0625F;
                    int gui_size_x = data.getViewingScreen().getSizeX();
                    int gui_size_y = data.getViewingScreen().getSizeY();

                    int texture_size_x = data.getViewingScreen().getTexX();
                    int texture_size_y = data.getViewingScreen().getTexY();

                    model = new ModelScreen(gui_size_x, gui_size_y, texture_size_x, texture_size_y);

                    mat.push();

                    //thanks to Its_Meow from the MMD server for this rotation aid !
                    mat.translate(this.getEntityModel().bipedHead.rotationPointX / 16.0F, this.getEntityModel().bipedHead.rotationPointY / 16.0F,
                            this.getEntityModel().bipedHead.rotationPointZ / 16.0F);
                    mat.rotate(Vector3f.ZP.rotation(this.getEntityModel().bipedHead.rotateAngleZ));
                    mat.rotate(Vector3f.YP.rotation(this.getEntityModel().bipedHead.rotateAngleY));
                    mat.rotate(Vector3f.XP.rotation(this.getEntityModel().bipedHead.rotateAngleX));

                    double dx = ((double) gui_size_x / 2.0) * pixelScale;
                    double dy = ((double) gui_size_y / 2.0) * pixelScale;
                    double headToCenterOffset = pixelScale * 4;

                    mat.translate(-dx * 0.0625, -dy * 0.0625 - headToCenterOffset, -pixelScale * 16);

                    mat.scale(pixelScale, pixelScale, pixelScale);

                    IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutout(resLoc));
                    model.render(mat, ivertexbuilder, 0xffffff, 0xffffff, 1f, 1f, 1f, 1f);

                    mat.pop();

                }
            }
        });

    }

    private static class ModelScreen extends Model {

        final RendererModelFlat renderer;

        public ModelScreen(int width, int height, int offx, int offy) {

            super(RenderType::getEntityCutout);

            this.textureHeight = offx;
            this.textureWidth = offy;

            renderer = new RendererModelFlat(this);
            renderer.setTextureSize(offx, offy);
            renderer.addBox(width, height);

        }

        @Override
        public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
        {

            renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }
}
