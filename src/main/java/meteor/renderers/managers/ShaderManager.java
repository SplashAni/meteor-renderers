package meteor.renderers.managers;

import meteor.renderers.Main;
import meteor.renderers.shaders.CustomShader;
import meteor.renderers.shaders.GlowShader;
import meteor.renderers.shaders.ShaderMode;
import meteor.renderers.shaders.ShaderRenderTarget;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ShaderManager {

    private final Map<ShaderMode, EnumMap<ShaderRenderTarget, CustomShader>> shaders =
        new EnumMap<>(ShaderMode.class);

    public ShaderManager() {
        registerShader(ShaderMode.GLOW);
    }

    private void registerShader(ShaderMode mode) {
        ShaderPipelinesManager pipelines = Main.SHADER_PIPELINE_MANAGER;

        EnumMap<ShaderRenderTarget, CustomShader> perTarget =
            new EnumMap<>(ShaderRenderTarget.class);

        for (ShaderRenderTarget target : ShaderRenderTarget.values()) {
            perTarget.put(target, new GlowShader(target, pipelines.getPipeline(mode.name().toLowerCase(), target == ShaderRenderTarget.HANDS)));
            System.out.println("Put "+mode +" the target "+perTarget);
        }

        shaders.put(mode, perTarget);
    }

    public List<CustomShader> getShaders() {
        List<CustomShader> result = new ArrayList<>();

        for (EnumMap<ShaderRenderTarget, CustomShader> perTarget : shaders.values()) {
            result.addAll(perTarget.values());
        }

        return result;
    }

    public CustomShader getShader(ShaderMode mode, ShaderRenderTarget target) {
        EnumMap<ShaderRenderTarget, CustomShader> map = shaders.get(mode);
        return map != null ? map.get(target) : null;
    }

}
