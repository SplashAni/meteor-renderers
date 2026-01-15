package meteor.renderers.shaders;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import meteor.renderers.modules.HandShader;
import meteor.renderers.uniforms.GlowShaderUniforms;
import meteordevelopment.meteorclient.renderer.MeshRenderer;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.joml.Vector4f;

public class GlowShader extends CustomShader {

    HandShader handShader;
    public GlowShader(RenderPipeline pipeline) {
        super(pipeline);
    }

    @Override
    protected boolean shouldDraw() {
        if (handShader == null) handShader = Modules.get().get(HandShader.class);
        return true;
    }

    @Override
    protected void setupPass(MeshRenderer renderer) {

        Vector4f lineColor = new Vector4f(
            handShader.lineColor.get().r / 255f,
            handShader.lineColor.get().g / 255f,
            handShader.lineColor.get().b / 255f,
            handShader.lineColor.get().a / 255f
        );

        Vector4f fillColor = new Vector4f(
            handShader.fillColor.get().r / 255f,
            handShader.fillColor.get().g / 255f,
            handShader.fillColor.get().b / 255f,
            handShader.fillColor.get().a / 255f
        );

        renderer.uniform("GlowUniforms", GlowShaderUniforms.write(
            handShader.radius.get(),
            lineColor,
            fillColor
        ));
    }


}
