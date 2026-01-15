package meteor.renderers.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import meteor.renderers.uniforms.GlowShaderUniforms;
import meteordevelopment.meteorclient.renderer.MeshUniforms;
import meteordevelopment.meteorclient.utils.render.postprocess.ChamsShader;
import meteordevelopment.meteorclient.utils.render.postprocess.OutlineUniforms;
import meteordevelopment.meteorclient.utils.render.postprocess.PostProcessShader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {
    @Inject(method = "flipFrame", at = @At("TAIL"))
    private static void flipFrame(CallbackInfo info) {
        GlowShaderUniforms.flipFrame();
    }
}
