package meteor.renderers.util;

import meteor.renderers.shaders.CustomShader;
import meteordevelopment.meteorclient.mixininterface.IEntityRenderState;
import meteordevelopment.meteorclient.utils.OutlineRenderCommandQueue;
import meteordevelopment.meteorclient.utils.render.NoopImmediateVertexConsumerProvider;
import meteordevelopment.meteorclient.utils.render.NoopOutlineVertexConsumerProvider;
import meteordevelopment.meteorclient.utils.render.WrapperImmediateVertexConsumerProvider;
import meteordevelopment.meteorclient.utils.render.color.Color;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.RenderDispatcher;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

import static meteordevelopment.meteorclient.MeteorClient.mc;

public class EntityShaderPass {

    private final OutlineRenderCommandQueue outlineRenderCommandQueue = new OutlineRenderCommandQueue(); // store entitiy gemotry data
    private RenderDispatcher renderDispatcher;
    private VertexConsumerProvider provider;


    public void setup(MatrixStack matrices, CustomShader shader, WorldRenderState worldRenderState) {
        if (renderDispatcher == null) {
            renderDispatcher = new RenderDispatcher(outlineRenderCommandQueue, mc.getBlockRenderManager(), new WrapperImmediateVertexConsumerProvider(() -> provider), mc.getAtlasManager(), NoopOutlineVertexConsumerProvider.INSTANCE, NoopImmediateVertexConsumerProvider.INSTANCE, mc.textRenderer);
        }
        renderEntitiesToFBO(worldRenderState, shader, matrices);
    }

    private void renderEntitiesToFBO(WorldRenderState worldState, CustomShader postProcessShader, MatrixStack matrices) {
        var camera = worldState.cameraRenderState.pos;

        boolean didRenderEntity = false;

        for (var state : worldState.entityRenderStates) {

            Entity entity = ((IEntityRenderState) state).meteor$getEntity();
            if (entity == null) continue;

            outlineRenderCommandQueue.setColor(Color.BLUE);

            assert mc.world != null;

            var renderer = ((IWorldRenderer) mc.worldRenderer).meteor_renderer$getEntityRendererManager().getRenderer(state);

            var offset = renderer.getPositionOffset(state);

            matrices.push();
            matrices.translate(state.x - camera.x + offset.x, state.y - camera.y + offset.y, state.z - camera.z + offset.z);

            renderer.render(state, matrices, outlineRenderCommandQueue, worldState.cameraRenderState);
            matrices.pop();

            didRenderEntity = true;
        }

        if (!didRenderEntity) return;

        ((IWorldRenderer) mc.worldRenderer).meteor_renderer$save(postProcessShader.framebuffer);
        provider = postProcessShader.vertexConsumerProvider;

        renderDispatcher.render();
        outlineRenderCommandQueue.onNextFrame();

        provider = null;

        ((IWorldRenderer) mc.worldRenderer).meteor_renderer$restore();
    }

}
