package meteor.renderers.modules;

import meteor.renderers.Main;
import meteor.renderers.event.HandRenderEvent;
import meteor.renderers.util.ShaderMode;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public class HandShader extends Module {

    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();

    private final Setting<ShaderMode> shaderMode = sgGeneral.add(new EnumSetting.Builder<ShaderMode>()
        .name("shader-mode")
        .description("Which kind of shader to use.")
        .defaultValue(ShaderMode.GLOW)
        .build()
    );

    public HandShader() {
        super(Main.RENDERER_CATEGORY, "hand-shader", "");
    }

    @EventHandler
    public void renderHand(HandRenderEvent event) {

        Main.SHADER_MANAGER.getCustomShader(ShaderMode.GLOW).setRenderingHand(true);
        Main.SHADER_MANAGER.getCustomShader(ShaderMode.GLOW).render();

    }

}
