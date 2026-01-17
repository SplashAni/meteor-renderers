package meteor.renderers.mixin;

import meteor.renderers.Main;
import meteor.renderers.managers.ShaderManager;
import meteor.renderers.shaders.ShaderRenderTarget;
import meteordevelopment.meteorclient.events.render.Render2DEvent;
import meteordevelopment.meteorclient.utils.render.postprocess.PostProcessShader;
import meteordevelopment.meteorclient.utils.render.postprocess.PostProcessShaders;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static meteordevelopment.meteorclient.MeteorClient.mc;

@Mixin(value = PostProcessShaders.class, remap = false)
public class PostProcessShadersMixin {
    @Inject(method = "init", at = @At(value = "HEAD"))
    private static void init(CallbackInfo ci) {
        Main.SHADER_MANAGER = new ShaderManager();
    }

    @Inject(method = "beginRender", at = @At(value = "HEAD"))
    private static void beginRender(CallbackInfo ci) {
        Main.SHADER_MANAGER.getShaders().forEach(PostProcessShader::clearTexture);
    }

    @Inject(method = "onResized", at = @At(value = "HEAD"))
    private static void onResized(int width, int height, CallbackInfo ci) {
        if (mc == null) return;

        Main.SHADER_MANAGER.getShaders().forEach(customShader -> customShader.onResized(width, height));
    }

    @Inject(method = "onRender", at = @At(value = "HEAD"))
    private static void onRender(Render2DEvent event, CallbackInfo ci) {
        Main.SHADER_MANAGER.getShaders().stream().filter(customShader -> customShader.getTarget() != ShaderRenderTarget.HANDS).forEachOrdered(PostProcessShader::render);
    }
}
