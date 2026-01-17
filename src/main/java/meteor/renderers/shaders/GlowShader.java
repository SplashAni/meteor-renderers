package meteor.renderers.shaders;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import meteor.renderers.modules.EntityShader;
import meteor.renderers.modules.HandShader;
import meteordevelopment.meteorclient.renderer.MeshRenderer;
import meteordevelopment.meteorclient.systems.modules.Modules;

public class GlowShader extends CustomShader {

    private HandShader hand;
    private EntityShader entity;

    public GlowShader(ShaderRenderTarget target, RenderPipeline pipeline) {
        super(target, pipeline);
    }

    @Override
    protected boolean shouldDraw() {

        if (hand == null) {
            hand = Modules.get().get(HandShader.class);
            entity = Modules.get().get(EntityShader.class);
        }

        return switch (target) {
            case HANDS -> hand.shouldRender() && hand.isActive();
            case ENTITY -> entity.shouldRender() && entity.isActive();
        };

    }

    @Override
    public void render() {
        if (target == ShaderRenderTarget.HANDS) renderingHand = true; // pass the depth buffer this way 😩
        super.render();
    }

    @Override
    protected void setupPass(MeshRenderer renderer) {
        switch (target) {
            case HANDS -> hand.setupUniforms(renderer);
            case ENTITY -> entity.setupUniforms(renderer);
        }
    }
}
