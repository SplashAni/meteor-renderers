package meteor.renderers.mixin;

import meteor.renderers.event.HandRenderEvent;
import meteordevelopment.meteorclient.MeteorClient;
import net.minecraft.client.render.GameRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "renderHand", at = @At("TAIL"))
    private void renderHand(float tickProgress, boolean sleeping, Matrix4f positionMatrix, CallbackInfo ci) {
        MeteorClient.EVENT_BUS.post(new HandRenderEvent());
    }
}
