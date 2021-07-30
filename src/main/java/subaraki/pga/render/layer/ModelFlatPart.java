package subaraki.pga.render.layer;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Stream;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ModelFlatPart {

    public float x;
    public float y;
    public float z;
    public boolean visible = true;
    private final List<ModelFlatPart.CubeFlat> cubes;

    public ModelFlatPart(List<ModelFlatPart.CubeFlat> p_171306_) {

        this.cubes = p_171306_;
    }

    public void render(PoseStack p_104302_, VertexConsumer p_104303_, int p_104304_, int p_104305_)
    {

        this.render(p_104302_, p_104303_, p_104304_, p_104305_, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void render(PoseStack p_104307_, VertexConsumer p_104308_, int p_104309_, int p_104310_, float p_104311_, float p_104312_, float p_104313_, float p_104314_)
    {

        if (this.visible)
        {
            if (!this.cubes.isEmpty())
            {
                p_104307_.pushPose();
                this.compile(p_104307_.last(), p_104308_, p_104309_, p_104310_, p_104311_, p_104312_, p_104313_, p_104314_);

                p_104307_.popPose();
            }
        }
    }

    private void compile(PoseStack.Pose p_104291_, VertexConsumer p_104292_, int p_104293_, int p_104294_, float p_104295_, float p_104296_, float p_104297_, float p_104298_)
    {

        for (ModelFlatPart.CubeFlat modelFlatPart$CubeFlat : this.cubes)
        {
            modelFlatPart$CubeFlat.compile(p_104291_, p_104292_, p_104293_, p_104294_, p_104295_, p_104296_, p_104297_, p_104298_);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class CubeFlat {

        private final ModelFlatPart.PolygonFlat[] polygons;
        public final float minX;
        public final float minY;
        public final float minZ;
        public final float maxX;
        public final float maxY;
        public final float maxZ;

        public CubeFlat(int texOffX, int texOffY, float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ, boolean mirror, float texWidth, float texHeight) {

            this.minX = x;
            this.minY = y;
            this.minZ = z;
            this.maxX = x + width;
            this.maxY = y + height;
            this.maxZ = z + depth;
            this.polygons = new ModelFlatPart.PolygonFlat[2];
            float f = x + width;
            float f1 = y + height;
            float f2 = z + depth;
            x = x - deltaX;
            y = y - deltaY;
            z = z - deltaZ;
            f = f + deltaX;
            f1 = f1 + deltaY;
            f2 = f2 + deltaZ;
            if (mirror)
            {
                float f3 = f;
                f = x;
                x = f3;
            }

            ModelFlatPart.VertexFlat modelFlatPart$vertex7 = new ModelFlatPart.VertexFlat(x, y, z, 0.0F, 0.0F);
            ModelFlatPart.VertexFlat modelFlatPart$vertex = new ModelFlatPart.VertexFlat(f, y, z, 0.0F, 8.0F);
            ModelFlatPart.VertexFlat modelFlatPart$vertex1 = new ModelFlatPart.VertexFlat(f, f1, z, 8.0F, 8.0F);
            ModelFlatPart.VertexFlat modelFlatPart$vertex2 = new ModelFlatPart.VertexFlat(x, f1, z, 8.0F, 0.0F);
            ModelFlatPart.VertexFlat modelFlatPart$vertex3 = new ModelFlatPart.VertexFlat(x, y, f2, 0.0F, 0.0F);
            ModelFlatPart.VertexFlat modelFlatPart$vertex4 = new ModelFlatPart.VertexFlat(f, y, f2, 0.0F, 8.0F);
            ModelFlatPart.VertexFlat modelFlatPart$vertex5 = new ModelFlatPart.VertexFlat(f, f1, f2, 8.0F, 8.0F);
            ModelFlatPart.VertexFlat modelFlatPart$vertex6 = new ModelFlatPart.VertexFlat(x, f1, f2, 8.0F, 0.0F);
            float f5 = (float) texOffX + depth;
            float f6 = (float) texOffX + depth + width;
            float f8 = (float) texOffX + depth + width + depth;
            float f9 = (float) texOffX + depth + width + depth + width;
            float f11 = (float) texOffY + depth;
            float f12 = (float) texOffY + depth + height;
            this.polygons[0] = new ModelFlatPart.PolygonFlat(
                    new ModelFlatPart.VertexFlat[] { modelFlatPart$vertex, modelFlatPart$vertex7, modelFlatPart$vertex2, modelFlatPart$vertex1 }, f5, f11, f6,
                    f12, texWidth, texHeight, mirror, Direction.NORTH);
            this.polygons[1] = new ModelFlatPart.PolygonFlat(
                    new ModelFlatPart.VertexFlat[] { modelFlatPart$vertex3, modelFlatPart$vertex4, modelFlatPart$vertex5, modelFlatPart$vertex6 }, f8, f11, f9,
                    f12, texWidth, texHeight, mirror, Direction.SOUTH);
        }

        public void compile(PoseStack.Pose stack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
        {

            Matrix4f matrix4f = stack.pose();
            Matrix3f matrix3f = stack.normal();

            for (ModelFlatPart.PolygonFlat ModelFlatPart$polygon : this.polygons)
            {
                Vector3f vector3f = ModelFlatPart$polygon.normal.copy();
                vector3f.transform(matrix3f);
                float f = vector3f.x();
                float f1 = vector3f.y();
                float f2 = vector3f.z();

                for (ModelFlatPart.VertexFlat modelFlatPart$vertex : ModelFlatPart$polygon.vertices)
                {
                    float f3 = modelFlatPart$vertex.pos.x() / 16.0F;
                    float f4 = modelFlatPart$vertex.pos.y() / 16.0F;
                    float f5 = modelFlatPart$vertex.pos.z() / 16.0F;
                    Vector4f vector4f = new Vector4f(f3, f4, f5, 1.0F);
                    vector4f.transform(matrix4f);
                    vertexConsumer.vertex(vector4f.x(), vector4f.y(), vector4f.z(), red, green, blue, alpha, modelFlatPart$vertex.u, modelFlatPart$vertex.v,
                            packedLightIn, packedOverlayIn, f, f1, f2);
                }
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    static class PolygonFlat {

        public final ModelFlatPart.VertexFlat[] vertices;
        public final Vector3f normal;

        public PolygonFlat(ModelFlatPart.VertexFlat[] p_104362_, float p_104363_, float p_104364_, float p_104365_, float p_104366_, float p_104367_, float p_104368_, boolean p_104369_, Direction p_104370_) {

            this.vertices = p_104362_;
            float f = 0.0F / p_104367_;
            float f1 = 0.0F / p_104368_;
            p_104362_[0] = p_104362_[0].remap(p_104365_ / p_104367_ - f, p_104364_ / p_104368_ + f1);
            p_104362_[1] = p_104362_[1].remap(p_104363_ / p_104367_ + f, p_104364_ / p_104368_ + f1);
            p_104362_[2] = p_104362_[2].remap(p_104363_ / p_104367_ + f, p_104366_ / p_104368_ - f1);
            p_104362_[3] = p_104362_[3].remap(p_104365_ / p_104367_ - f, p_104366_ / p_104368_ - f1);
            if (p_104369_)
            {
                int i = p_104362_.length;

                for (int j = 0; j < i / 2; ++j)
                {
                    ModelFlatPart.VertexFlat modelFlatPart$vertex = p_104362_[j];
                    p_104362_[j] = p_104362_[i - 1 - j];
                    p_104362_[i - 1 - j] = modelFlatPart$vertex;
                }
            }

            this.normal = p_104370_.step();
            if (p_104369_)
            {
                this.normal.mul(-1.0F, 1.0F, 1.0F);
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    static class VertexFlat {

        public final Vector3f pos;
        public final float u;
        public final float v;

        public VertexFlat(float p_104375_, float p_104376_, float p_104377_, float p_104378_, float p_104379_) {

            this(new Vector3f(p_104375_, p_104376_, p_104377_), p_104378_, p_104379_);
        }

        public ModelFlatPart.VertexFlat remap(float p_104385_, float p_104386_)
        {

            return new ModelFlatPart.VertexFlat(this.pos, p_104385_, p_104386_);
        }

        public VertexFlat(Vector3f p_104381_, float p_104382_, float p_104383_) {

            this.pos = p_104381_;
            this.u = p_104382_;
            this.v = p_104383_;
        }
    }
}