package subaraki.pga.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RendererModelFlat extends ModelRenderer {

    private float textureWidth = 0.0F;
    private float textureHeight = 0.0F;
    private int textureOffsetX;
    private int textureOffsetY;
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    public boolean mirror;
    public boolean showModel = true;
    private final ObjectList<ModelFlat> cubeList = new ObjectArrayList<>();

    public RendererModelFlat(Model model) {

        super(model);
    }

    public RendererModelFlat(Model model, int texOffX, int texOffY) {

        this(model.textureWidth, model.textureHeight, texOffX, texOffY);
        model.accept(this);
    }

    public RendererModelFlat(int textureWidthIn, int textureHeightIn, int textureOffsetXIn, int textureOffsetYIn) {

        super(textureWidthIn, textureHeightIn, textureOffsetXIn, textureOffsetYIn);
    }

    public ModelRenderer setTextureOffset(int x, int y)
    {

        this.textureOffsetX = x;
        this.textureOffsetY = y;
        return this;
    }

    public void addBox(int width, int height)
    {

        this.addBox(this.textureOffsetX, this.textureOffsetY, 0, 0, 0, width, height, 0, 0, 0, 0, false, false);
    }

    private void addBox(int texOffX, int texOffY, float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ, boolean mirorIn, boolean p_228305_13_)
    {

        this.cubeList
                .add(new ModelFlat(texOffX, texOffY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, mirorIn, this.textureWidth, this.textureHeight));
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn)
    {

        this.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
    {

        if (this.showModel)
        {
            if (!this.cubeList.isEmpty())
            {
                matrixStackIn.push();
                this.translateRotate(matrixStackIn);
                this.doRender(matrixStackIn.getLast(), bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

                matrixStackIn.pop();
            }
        }
    }

    @Override
    public void translateRotate(MatrixStack matrixStackIn)
    {

        matrixStackIn.translate((double) (this.rotationPointX / 16.0F), (double) (this.rotationPointY / 16.0F), (double) (this.rotationPointZ / 16.0F));
        if (this.rotateAngleZ != 0.0F)
        {
            matrixStackIn.rotate(Vector3f.ZP.rotation(this.rotateAngleZ));
        }

        if (this.rotateAngleY != 0.0F)
        {
            matrixStackIn.rotate(Vector3f.YP.rotation(this.rotateAngleY));
        }

        if (this.rotateAngleX != 0.0F)
        {
            matrixStackIn.rotate(Vector3f.XP.rotation(this.rotateAngleX));
        }

    }

    private void doRender(MatrixStack.Entry matrixEntryIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
    {

        Matrix4f matrix4f = matrixEntryIn.getMatrix();
        Matrix3f matrix3f = matrixEntryIn.getNormal();

        for (ModelFlat modelrenderer$modelbox : this.cubeList)
        {
            for (OneTexturedQuad the_quad : modelrenderer$modelbox.quads)
            {
                Vector3f vector3f = the_quad.normal.copy();
                vector3f.transform(matrix3f);
                float f = vector3f.getX();
                float f1 = vector3f.getY();
                float f2 = vector3f.getZ();

                for (int i = 0; i < 4; ++i)
                {
                    FlatPositionTextureVertex modelrenderer$positiontexturevertex = the_quad.vertexPositions[i];
                    float f3 = modelrenderer$positiontexturevertex.position.getX() / 16.0F;
                    float f4 = modelrenderer$positiontexturevertex.position.getY() / 16.0F;
                    float f5 = modelrenderer$positiontexturevertex.position.getZ() / 16.0F;
                    Vector4f vector4f = new Vector4f(f3, f4, f5, 1.0F);
                    vector4f.transform(matrix4f);
                    bufferIn.addVertex(vector4f.getX(), vector4f.getY(), vector4f.getZ(), red, green, blue, alpha, modelrenderer$positiontexturevertex.textureU,
                            modelrenderer$positiontexturevertex.textureV, packedOverlayIn, packedLightIn, f, f1, f2);
                }
            }

        }

    }

    /**
     * Returns the model renderer with the new texture parameters.
     */
    public ModelRenderer setTextureSize(int textureWidthIn, int textureHeightIn)
    {

        this.textureWidth = (float) textureWidthIn;
        this.textureHeight = (float) textureHeightIn;
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ModelFlat {

        private final OneTexturedQuad quads[];
        public final float posX1;
        public final float posY1;
        public final float posZ1;
        public final float posX2;
        public final float posY2;
        public final float posZ2;

        public ModelFlat(int texOffX, int texOffY, float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ, boolean mirorIn, float texWidth, float texHeight) {

            this.posX1 = x;
            this.posY1 = y;
            this.posZ1 = z;
            this.posX2 = x + width;
            this.posY2 = y + height;
            this.posZ2 = z + depth;
            this.quads = new OneTexturedQuad[2];
            float f = x + width;
            float f1 = y + height;
            float f2 = z + depth;
            x = x - deltaX;
            y = y - deltaY;
            z = z - deltaZ;
            f = f + deltaX;
            f1 = f1 + deltaY;
            f2 = f2 + deltaZ;
            if (mirorIn)
            {
                float f3 = f;
                f = x;
                x = f3;
            }

            FlatPositionTextureVertex positiontexturevertex7 = new FlatPositionTextureVertex(x, y, z, 0.0F, 0.0F);
            FlatPositionTextureVertex positiontexturevertex = new FlatPositionTextureVertex(f, y, z, 0.0F, 8.0F);
            FlatPositionTextureVertex positiontexturevertex1 = new FlatPositionTextureVertex(f, f1, z, 8.0F, 8.0F);
            FlatPositionTextureVertex positiontexturevertex2 = new FlatPositionTextureVertex(x, f1, z, 8.0F, 0.0F);
            FlatPositionTextureVertex positiontexturevertex3 = new FlatPositionTextureVertex(x, y, f2, 0.0F, 0.0F);
            FlatPositionTextureVertex positiontexturevertex4 = new FlatPositionTextureVertex(f, y, f2, 0.0F, 8.0F);
            FlatPositionTextureVertex positiontexturevertex5 = new FlatPositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
            FlatPositionTextureVertex positiontexturevertex6 = new FlatPositionTextureVertex(x, f1, f2, 8.0F, 0.0F);
            float f5 = (float) texOffX + depth;
            float f6 = (float) texOffX + depth + width;
            float f10 = (float) texOffY;
            float f11 = (float) texOffY + depth;
            float f12 = (float) texOffY + depth + height;
            this.quads[0] =

                    new OneTexturedQuad(
                            new FlatPositionTextureVertex[] { positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6 },
                            f5, f10, f6, f12, texWidth, texHeight, false, Direction.SOUTH);

            this.quads[1] =

                    new OneTexturedQuad(
                            new FlatPositionTextureVertex[] { positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1 },
                            f6, f11, f5, f12, texWidth, texHeight, false, Direction.NORTH);

        }
    }

    @OnlyIn(Dist.CLIENT)
    static class FlatPositionTextureVertex {

        public final Vector3f position;
        public final float textureU;
        public final float textureV;

        public FlatPositionTextureVertex(float x, float y, float z, float texU, float texV) {

            this(new Vector3f(x, y, z), texU, texV);
        }

        public FlatPositionTextureVertex setTextureUV(float texU, float texV)
        {

            return new FlatPositionTextureVertex(this.position, texU, texV);
        }

        public FlatPositionTextureVertex(Vector3f posIn, float texU, float texV) {

            this.position = posIn;
            this.textureU = texU;
            this.textureV = texV;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class OneTexturedQuad {

        public final FlatPositionTextureVertex[] vertexPositions;
        public final Vector3f normal;

        public OneTexturedQuad(FlatPositionTextureVertex[] positionsIn, float u1, float v1, float u2, float v2, float texWidth, float texHeight, boolean mirrorIn, Direction directionIn) {

            this.vertexPositions = positionsIn;
            float f = 0.0F / texWidth;
            float f1 = 0.0F / texHeight;
            positionsIn[0] = positionsIn[0].setTextureUV(u2 / texWidth - f, v1 / texHeight + f1);
            positionsIn[1] = positionsIn[1].setTextureUV(u1 / texWidth + f, v1 / texHeight + f1);
            positionsIn[2] = positionsIn[2].setTextureUV(u1 / texWidth + f, v2 / texHeight - f1);
            positionsIn[3] = positionsIn[3].setTextureUV(u2 / texWidth - f, v2 / texHeight - f1);
            if (mirrorIn)
            {
                int i = positionsIn.length;

                for (int j = 0; j < i / 2; ++j)
                {
                    FlatPositionTextureVertex modelrenderer$positiontexturevertex = positionsIn[j];
                    positionsIn[j] = positionsIn[i - 1 - j];
                    positionsIn[i - 1 - j] = modelrenderer$positiontexturevertex;
                }
            }

            this.normal = directionIn.toVector3f();
            if (mirrorIn)
            {
                this.normal.mul(-1.0F, 1.0F, 1.0F);
            }
        }
    }
}
