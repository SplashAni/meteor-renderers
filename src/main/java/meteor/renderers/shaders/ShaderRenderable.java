package meteor.renderers.shaders;

import meteordevelopment.meteorclient.renderer.MeshRenderer;

public interface ShaderRenderable {
    boolean shouldRender();
    void setupUniforms(MeshRenderer renderer);
}
