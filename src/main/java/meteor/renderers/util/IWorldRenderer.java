package meteor.renderers.util;

import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.entity.EntityRenderManager;

public interface IWorldRenderer {
    EntityRenderManager meteor_renderer$getEntityRendererManager();

    void meteor_renderer$save(Framebuffer framebuffer);

    void meteor_renderer$restore();
}
