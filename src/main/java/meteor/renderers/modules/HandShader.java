package meteor.renderers.modules;

import meteor.renderers.Main;
import meteor.renderers.event.HandRenderEvent;
import meteor.renderers.util.ShaderMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.render.ESP;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;

public class HandShader extends Module {

    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    private final SettingGroup sgRender = settings.createGroup("Render");

    private final Setting<ShaderMode> shaderMode = sgGeneral.add(new EnumSetting.Builder<ShaderMode>()
        .name("shader-mode")
        .description("Which kind of shader to use.")
        .defaultValue(ShaderMode.GLOW)
        .build()
    );
    public final Setting<Integer> radius = sgGeneral.add(new IntSetting.Builder()
        .name("radius")
        .defaultValue(1)
        .min(1)
        .sliderMax(5)
        .build()
    );

    public final Setting<SettingColor> lineColor = sgGeneral.add(new ColorSetting.Builder()
        .name("line-color")
        .description("The monster's color.")
        .defaultValue(new SettingColor(0, 255, 125, 255))
        .build()
    );
    public final Setting<SettingColor> fillColor = sgGeneral.add(new ColorSetting.Builder()
        .name("fill-color")
        .description("The monster's color.")
        .defaultValue(new SettingColor(0, 255, 125, 125))
        .build()
    );

    public HandShader() {
        super(Main.RENDERER_CATEGORY, "hand-shader", "");
    }

    @EventHandler
    public void renderHand(HandRenderEvent event) {
        Main.SHADER_MANAGER.getCustomShader(ShaderMode.GLOW).render();

    }

}
