package subaraki.pga.render.layer;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;

public class RendererModelFlat extends RendererModel {

    private int textureOffsetX;
    private int textureOffsetY;

    public RendererModelFlat(Model model) {

        super(model);
    }

    @Override
    public RendererModelFlat addBox(float offX, float offY, float offZ, int width, int height, int depth) {

        this.cubeList.add(new FlatBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F));
        return this;
    }

    @Override
    public RendererModelFlat setTextureSize(int textureWidthIn, int textureHeightIn) {

        this.textureWidth = (float) textureWidthIn;
        this.textureHeight = (float) textureHeightIn;
        return this;
    }
}
