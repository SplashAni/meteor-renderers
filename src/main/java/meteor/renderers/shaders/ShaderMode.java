package meteor.renderers.shaders;

import meteor.renderers.uniforms.ShaderUniform;
import meteor.renderers.uniforms.GlowShaderUniforms;

public enum ShaderMode {
    GLOW(new GlowShaderUniforms());

    private final ShaderUniform shaderUniform;

    ShaderMode(ShaderUniform uniform) {
        this.shaderUniform = uniform;
    }

    public ShaderUniform getShaderUniform() {
        return shaderUniform;
    }
}
