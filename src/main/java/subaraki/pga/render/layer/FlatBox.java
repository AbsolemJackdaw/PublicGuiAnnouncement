package subaraki.pga.render.layer;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.client.renderer.model.PositionTextureVertex;
import net.minecraft.client.renderer.model.TexturedQuad;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlatBox extends ModelBox {

    private final PositionTextureVertex[] vertices;
    private final TexturedQuad[] quads;
    public final float posX1;
    public final float posY1;
    public final float posZ1;
    public final float posX2;
    public final float posY2;
    public final float posZ2;
    public String boxName;

    public FlatBox(RendererModel renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta) {

        this(renderer, texU, texV, x, y, z, dx, dy, dz, delta, renderer.mirror);
    }

    public FlatBox(RendererModel renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta, boolean mirror) {

        super(renderer, texU, texV, x, y, z, dx, dy, dz, delta, mirror);
        this.posX1 = x;
        this.posY1 = y;
        this.posZ1 = z;

        this.posX2 = x + (float) dx;
        this.posY2 = y + (float) dy;
        this.posZ2 = z + (float) dz;

        this.vertices = new PositionTextureVertex[8];
        this.quads = new TexturedQuad[1];
        float f = x + (float) dx;
        float f1 = y + (float) dy;
        float f2 = z + (float) dz;

        x = x - delta;
        y = y - delta;
        z = z - delta;

        f = f + delta;
        f1 = f1 + delta;
        f2 = f2 + delta;

        if (mirror) {
            float f3 = f;
            f = x;
            x = f3;
        }

        PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);
        PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, y, z, 0.0F, 8.0F);
        PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, z, 8.0F, 8.0F);
        PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(x, f1, z, 8.0F, 0.0F);
        PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(x, y, f2, 0.0F, 0.0F);
        PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, y, f2, 0.0F, 8.0F);
        PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
        PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(x, f1, f2, 8.0F, 0.0F);
        this.vertices[0] = positiontexturevertex7;
        this.vertices[1] = positiontexturevertex;
        this.vertices[2] = positiontexturevertex1;
        this.vertices[3] = positiontexturevertex2;
        this.vertices[4] = positiontexturevertex3;
        this.vertices[5] = positiontexturevertex4;
        this.vertices[6] = positiontexturevertex5;
        this.vertices[7] = positiontexturevertex6;

        this.quads[0] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1 }, texU + dz + dx,
                texV + dz, texU + dz, texV + dz + dy, renderer.textureWidth, renderer.textureHeight);

        for (TexturedQuad texturedquad : this.quads) {
            texturedquad.flipFace();
        }

    }

    @Override
    public void render(BufferBuilder renderer, float scale) {

        for (TexturedQuad texturedquad : this.quads) {
            texturedquad.draw(renderer, scale);
        }

    }

    @Override
    public FlatBox setBoxName(String name) {

        this.boxName = name;
        return this;
    }
}