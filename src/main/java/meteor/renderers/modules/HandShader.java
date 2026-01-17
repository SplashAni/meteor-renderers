package meteor.renderers.modules;

import meteor.renderers.Main;
import meteor.renderers.event.HandRenderEvent;
import meteor.renderers.shaders.ShaderRenderTarget;
import meteor.renderers.shaders.ShaderRenderable;
import meteor.renderers.uniforms.GlowShaderUniforms;
import meteor.renderers.shaders.ShaderMode;
import meteordevelopment.meteorclient.renderer.MeshRenderer;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import org.joml.Vector4f;

public class HandShader extends Module implements ShaderRenderable {

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
        Main.SHADER_MANAGER.getShader(shaderMode.get(), ShaderRenderTarget.HANDS).render();
    }

    @Override
    public boolean shouldRender() {
        return true;
    }

    @Override
    public void setupUniforms(MeshRenderer renderer) {
        switch (shaderMode.get()) {

            case GLOW -> {
                Vector4f lines = new Vector4f(lineColor.get().r / 255f, lineColor.get().g / 255f, lineColor.get().b / 255f, lineColor.get().a / 255f);

                Vector4f fill = new Vector4f(fillColor.get().r / 255f, fillColor.get().g / 255f, fillColor.get().b / 255f, fillColor.get().a / 255f);

                renderer.uniform("GlowUniforms", GlowShaderUniforms.write(radius.get(), lines, fill));
            }
            case GRADIENT -> {

            }
        }
    }
}
