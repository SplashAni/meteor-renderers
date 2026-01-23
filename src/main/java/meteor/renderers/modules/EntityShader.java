package meteor.renderers.modules;

import meteor.renderers.Main;
import meteor.renderers.event.EntityRenderEvent;
import meteor.renderers.shaders.ShaderMode;
import meteor.renderers.shaders.ShaderRenderTarget;
import meteor.renderers.shaders.ShaderRenderable;
import meteor.renderers.uniforms.GlowShaderUniforms;
import meteor.renderers.util.EntityShaderPass;
import meteordevelopment.meteorclient.renderer.MeshRenderer;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import org.joml.Vector4f;

public class EntityShader extends Module implements ShaderRenderable {

    private final SettingGroup sgGeneral = this.settings.getDefaultGroup(); // todo automate ts..
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
    private final SettingGroup sgRender = settings.createGroup("Render");
    private final Setting<ShaderMode> shaderMode = sgGeneral.add(new EnumSetting.Builder<ShaderMode>()
        .name("shader-mode")
        .description("Which kind of shader to use.")
        .defaultValue(ShaderMode.GLOW)
        .build()
    );
    EntityShaderPass entityShaderPass;

    public EntityShader() {
        super(Main.RENDERER_CATEGORY, "entity-shader", "d");
    }

    @Override
    public void onActivate() {
        entityShaderPass = new EntityShaderPass();
        super.onActivate();
    }

    @Override
    public boolean shouldRender() {
        return true;
    }

    @Override
    public void setupUniforms(MeshRenderer renderer) {
        Vector4f lines = new Vector4f(lineColor.get().r / 255f, lineColor.get().g / 255f, lineColor.get().b / 255f, lineColor.get().a / 255f);

        Vector4f fill = new Vector4f(fillColor.get().r / 255f, fillColor.get().g / 255f, fillColor.get().b / 255f, fillColor.get().a / 255f);

        GlowShaderUniforms uniforms = (GlowShaderUniforms) shaderMode.get().getShaderUniform();

        renderer.uniform("GlowUniforms", uniforms.write(ShaderRenderTarget.ENTITY.ordinal(), radius.get(), lines, fill));
    }

    @EventHandler
    public void onEntityRender(EntityRenderEvent event) {
        entityShaderPass.setup(event.getMatrices(), Main.SHADER_MANAGER.getShader(ShaderMode.GLOW, ShaderRenderTarget.ENTITY), event.getRenderStates());
    }
}
