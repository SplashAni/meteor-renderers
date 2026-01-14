package meteor.renderers.mixin;

import meteor.renderers.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftClient.class)
public abstract class ExampleMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void clinit(RunArgs args, CallbackInfo ci) {
        Main.LOG.info("Hello from ExampleMixin!");
    }
}
