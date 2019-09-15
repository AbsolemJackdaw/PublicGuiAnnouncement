package subaraki.pga.render.layer;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import subaraki.pga.capability.ScreenData;

public class LayerScreen extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    public LayerScreen(PlayerRenderer renderer) {

        super(renderer);
    }

    @Override
    public boolean shouldCombineTextures() {

        return false;
    }

    @Override
    public void render(AbstractClientPlayerEntity entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        ScreenData data = ScreenData.get((PlayerEntity) entityIn);

        if (Minecraft.getInstance().currentScreen instanceof InventoryScreen || Minecraft.getInstance().currentScreen instanceof CreativeScreen)
            return;

        if (data != null && data.getViewingScreen() != null) {

            // if (System.currentTimeMillis() % 60 == 0) {
            // ScreenMod.LOG.debug(data.getViewingScreen().getResLoc() + " " +
            // entityIn.getDisplayName().getFormattedText());
            // }

            ResourceLocation resLoc = data.lookupResloc(data.getViewingScreen().getRefName());

            if (resLoc != null) {

                double pixelScale = 0.0625;
                int x = data.getViewingScreen().getSizeX();
                int y = data.getViewingScreen().getSizeY();

                Model m = new Model();
                m.textureWidth = data.getViewingScreen().getTexX();
                m.textureHeight = data.getViewingScreen().getTexY();

                RendererModelFlat model = new RendererModelFlat(m).addBox(0, 0, 0, x, y, 0);

                GlStateManager.pushMatrix();

                GlStateManager.rotated(netHeadYaw, 0, 1, 0);
                GlStateManager.rotated(headPitch, 1, 0, 0);

                double dx = ((double) x / 2.0) * pixelScale;
                double dy = ((double) y / 2.0) * pixelScale;
                double headToCenterOffset = pixelScale * 4;

                GlStateManager.translated(-dx * 0.0625, -dy * 0.0625 - headToCenterOffset, -pixelScale * 16);

                GlStateManager.scaled(pixelScale, pixelScale, pixelScale);

                bindTexture(resLoc);
                model.render((float) pixelScale);

                GlStateManager.popMatrix();
            }
        }
    }
}
