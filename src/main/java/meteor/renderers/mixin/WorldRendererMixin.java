package meteor.renderers.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import meteor.renderers.Main;
import meteor.renderers.event.EntityRenderEvent;
import meteor.renderers.modules.EntityShader;
import meteor.renderers.shaders.CustomShader;
import meteor.renderers.shaders.ShaderRenderTarget;
import meteor.renderers.util.IWorldRenderer;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.Handle;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin implements IWorldRenderer {

    @Shadow
    @Final
    private EntityRenderManager entityRenderManager;
    @Shadow
    private Framebuffer entityOutlineFramebuffer;
    @Shadow
    @Final
    private DefaultFramebufferSet framebufferSet;
    @Unique
    private Stack<Handle<Framebuffer>> framebufferHandleStack;
    @Unique
    private Stack<Framebuffer> framebufferStack;

    @Override
    public EntityRenderManager meteor_renderer$getEntityRendererManager() {
        return entityRenderManager;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        framebufferStack = new ObjectArrayList<>();
        framebufferHandleStack = new ObjectArrayList<>();
    }


    @Override
    public void meteor_renderer$save(Framebuffer framebuffer) {
        framebufferStack.push(this.entityOutlineFramebuffer);
        this.entityOutlineFramebuffer = framebuffer;

        framebufferHandleStack.push(this.framebufferSet.entityOutlineFramebuffer);
        this.framebufferSet.entityOutlineFramebuffer = () -> framebuffer;
    }

    @Override
    public void meteor_renderer$restore() {
        this.entityOutlineFramebuffer = framebufferStack.pop();
        this.framebufferSet.entityOutlineFramebuffer = framebufferHandleStack.pop();
    }

    @Inject(method = "method_62214", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/OutlineVertexConsumerProvider;draw()V"))
    private void method_62214(CallbackInfo ci) {
        for (CustomShader shader : Main.SHADER_MANAGER.getShaders()) {
            if (shader.getTarget() == ShaderRenderTarget.ENTITY) {
                shader.submitVertices();
            }
        }
    }

    @Inject(method = "pushEntityRenders", at = @At("TAIL"))
    private void pushEntityRenders(MatrixStack matrices, WorldRenderState renderStates, OrderedRenderCommandQueue queue, CallbackInfo ci) {
        MeteorClient.EVENT_BUS.post(new EntityRenderEvent(matrices, renderStates));
    }

    @ModifyExpressionValue(method = "fillEntityRenderStates", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;isRenderingReady(Lnet/minecraft/util/math/BlockPos;)Z"))
    boolean fillEntityRenderStates(boolean original) {
        if (Modules.get().get(EntityShader.class).isActive()) return true;
        else return original;

    }
}
