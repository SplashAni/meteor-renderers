package meteor.renderers.shaders;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import meteordevelopment.meteorclient.utils.render.postprocess.PostProcessShader;

public abstract class CustomShader extends PostProcessShader {

    boolean renderingHand;

    protected CustomShader(RenderPipeline pipeline) {
        super(pipeline);
    }


    @Override
    protected boolean shouldDraw() {
        return true;
    }

    public boolean isRenderingHand() {
        return renderingHand;
    }

    public void setRenderingHand(boolean renderingHand) {
        this.renderingHand = renderingHand;
    }

}
