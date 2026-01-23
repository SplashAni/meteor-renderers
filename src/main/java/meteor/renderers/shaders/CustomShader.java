package meteor.renderers.shaders;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import meteor.renderers.util.IWorldRenderer;
import meteordevelopment.meteorclient.utils.render.CustomOutlineVertexConsumerProvider;
import meteordevelopment.meteorclient.utils.render.postprocess.PostProcessShader;

import static meteordevelopment.meteorclient.MeteorClient.mc;

public abstract class CustomShader extends PostProcessShader {

    public final CustomOutlineVertexConsumerProvider vertexConsumerProvider;
    ShaderRenderTarget target;


    protected CustomShader(ShaderRenderTarget target, RenderPipeline pipeline) {
        super(pipeline);
        this.target = target;
        this.vertexConsumerProvider = new CustomOutlineVertexConsumerProvider();
    }

    @Override
    protected boolean shouldDraw() {
        return true;
    }


    @Override
    protected void preDraw() {
        if (target == ShaderRenderTarget.ENTITY) {
            ((IWorldRenderer) mc.worldRenderer).meteor_renderer$save(framebuffer);
        }
        super.preDraw();
    }

    @Override
    protected void postDraw() {
        if (target == ShaderRenderTarget.ENTITY) {
            ((IWorldRenderer) mc.worldRenderer).meteor_renderer$restore();
        }
        super.postDraw();
    }

    public void submitVertices() {
        submitVertices(vertexConsumerProvider::draw);
    }

    public ShaderRenderTarget getTarget() {
        return target;
    }
}
