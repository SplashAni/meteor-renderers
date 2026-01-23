package meteor.renderers.uniforms;

import meteor.renderers.Main;

public abstract class ShaderUniform {

    protected ShaderUniform() {
        Main.UNIFORM_MANAGER.register(this);
    }


    public void clear() {
    }
}
