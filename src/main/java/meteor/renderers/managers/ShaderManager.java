package meteor.renderers.managers;

import meteor.renderers.Main;
import meteor.renderers.shaders.CustomShader;
import meteor.renderers.shaders.GlowShader;
import meteor.renderers.util.ShaderMode;

import java.util.ArrayList;
import java.util.List;

public class ShaderManager {
    public List<CustomShader> customShaders = new ArrayList<>();

    public ShaderManager() {
        ShaderPipelinesManager pipelines = Main.SHADER_PIPELINE_MANAGER;

        registerShader(new GlowShader(pipelines.HAND_GLOW_PIPELINE));

    }

    public CustomShader getCustomShader(ShaderMode shaderMode) {
        return switch (shaderMode) { // oh java my beloved
            case GLOW -> customShaders.getFirst(); // lol ts lowkey not tuff i cant lie
            case GRADIENT -> null;
        };
    }

    private void registerShader(CustomShader customShader) {
        if (!customShaders.contains(customShader)) {
            customShaders.add(customShader);
        }
    }
}
