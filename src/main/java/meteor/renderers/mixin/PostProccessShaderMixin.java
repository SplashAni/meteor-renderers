package meteor.renderers.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import meteor.renderers.shaders.CustomShader;
import meteordevelopment.meteorclient.renderer.MeshRenderer;
import meteordevelopment.meteorclient.utils.render.postprocess.PostProcessShader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static meteordevelopment.meteorclient.MeteorClient.mc;

@Mixin(value = PostProcessShader.class, remap = false)
public class PostProccessShaderMixin {

    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lmeteordevelopment/meteorclient/renderer/MeshRenderer;begin()Lmeteordevelopment/meteorclient/renderer/MeshRenderer;"))
    private MeshRenderer render(MeshRenderer original) { // adds the depth sampler to the shader for finding out where the hand is ... - SPLASH DEMON XD

        if ((Object) (this) instanceof CustomShader customShader) {
            if (customShader.isRenderingHand())
                System.out.println("rendering hand "+customShader.isRenderingHand());
                original.sampler("u_Depth", mc.getFramebuffer().getDepthAttachmentView(), RenderSystem.getSamplerCache().get(FilterMode.NEAREST));
        }

        return original;
    }
}
