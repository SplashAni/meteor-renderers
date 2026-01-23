package meteor.renderers.managers;

import meteor.renderers.uniforms.ShaderUniform;

import java.util.ArrayList;
import java.util.List;

public class UniformManager {

    private final List<ShaderUniform> uniforms = new ArrayList<>();

    public void register(ShaderUniform uniform) {
        uniforms.add(uniform);
    }

    public void flipAll() {
        uniforms.forEach(ShaderUniform::clear);
    }
}
