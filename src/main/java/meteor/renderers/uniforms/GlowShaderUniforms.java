package meteor.renderers.uniforms;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import net.minecraft.client.gl.DynamicUniformStorage;
import org.joml.Vector4f;

import java.nio.ByteBuffer;

public class GlowShaderUniforms {

    private static final int UNIFORM_SIZE =
        new Std140SizeCalculator().putInt().putVec4().putVec4().get(); // todo: automate ts

    private static final DynamicUniformStorage<Data> STORAGE =
        new DynamicUniformStorage<>("Glow Shader Uniforms", UNIFORM_SIZE, 16);


    public static void flipFrame() {
        STORAGE.clear();
    }

    public static GpuBufferSlice write(int target, int radius, Vector4f outlineColor, Vector4f fillColor) {
        return STORAGE.write(new Data(target, radius, outlineColor, fillColor));
    }

    private record Data(int target, int radius, Vector4f outlineColor, Vector4f fillColor)
        implements DynamicUniformStorage.Uploadable {

        @Override
        public void write(ByteBuffer buffer) {
            Std140Builder.intoBuffer(buffer)
                .putInt(target)
                .putInt(radius)
                .putVec4(outlineColor.x, outlineColor.y, outlineColor.z, outlineColor.w)
                .putVec4(fillColor.x, fillColor.y, fillColor.z, fillColor.w);
        }
    }
}
