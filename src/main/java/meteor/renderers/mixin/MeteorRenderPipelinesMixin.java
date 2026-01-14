package meteor.renderers.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import meteor.renderers.Main;
import meteor.renderers.managers.ShaderPipelinesManager;
import meteordevelopment.meteorclient.renderer.MeteorRenderPipelines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MeteorRenderPipelines.class, remap = false)
public abstract class MeteorRenderPipelinesMixin {

    @Shadow
    private native static RenderPipeline add(RenderPipeline pipeline);

    @Inject(method = "precompile", at = @At(value = "HEAD"))
    private static void precompile(CallbackInfo ci) {

        ShaderPipelinesManager shaderPipelinesManager = Main.SHADER_PIPELINE_MANAGER;

        if (!shaderPipelinesManager.isDidPreCompile()) {
            shaderPipelinesManager.getPreCompileList().forEach(MeteorRenderPipelinesMixin::add);
        }

    }
}
