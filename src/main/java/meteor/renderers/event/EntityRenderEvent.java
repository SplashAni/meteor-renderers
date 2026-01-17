package meteor.renderers.event;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.math.MatrixStack;

public class EntityRenderEvent {
    MatrixStack matrices;
    WorldRenderState renderStates;

    public EntityRenderEvent(MatrixStack matrices, WorldRenderState renderStates) {
        this.matrices = matrices;
        this.renderStates = renderStates;
    }

    public MatrixStack getMatrices() {
        return matrices;
    }

    public WorldRenderState getRenderStates() {
        return renderStates;
    }

}
