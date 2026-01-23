package meteor.renderers.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import meteor.renderers.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {
    @Inject(method = "flipFrame", at = @At("TAIL"))
    private static void flipFrame(CallbackInfo info) {
        Main.UNIFORM_MANAGER.flipAll();
    }
}
