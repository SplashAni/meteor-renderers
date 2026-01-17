package meteor.renderers.managers;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.renderer.ExtendedRenderPipelineBuilder;
import meteordevelopment.meteorclient.renderer.MeteorVertexFormats;
import net.minecraft.client.gl.UniformType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ShaderPipelinesManager {

    private final List<RenderPipeline> preCompileList = new ArrayList<>();
    private boolean didPreCompile;


    public RenderPipeline getPipeline(String name, boolean hands) {
        return registerPipeline(name, hands);
    }

    private RenderPipeline registerPipeline(String name, boolean hands) {
        RenderPipeline pipeline = buildPipeline(name, hands);

        if (!preCompileList.contains(pipeline)) {
            preCompileList.add(pipeline);
        }

        return pipeline;
    }

    private RenderPipeline buildPipeline(String name, boolean hands) {
        ExtendedRenderPipelineBuilder builder = (ExtendedRenderPipelineBuilder) new ExtendedRenderPipelineBuilder()
            .withLocation(Identifier.of("renderers", "pipeline/shaders/".concat(name)))
            .withVertexFormat(MeteorVertexFormats.POS2, VertexFormat.DrawMode.TRIANGLES)
            .withVertexShader(MeteorClient.identifier("shaders/post-process/base.vert"))
            .withFragmentShader(Identifier.of("renderers", "shaders/".concat(name).concat(".frag")))
            .withSampler("u_Texture")
            .withUniform("PostData", UniformType.UNIFORM_BUFFER)
            .withUniform("GlowUniforms",UniformType.UNIFORM_BUFFER)
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withDepthWrite(false)
            .withBlend(BlendFunction.TRANSLUCENT)
            .withCull(false);

        if (hands) builder.withSampler("u_Depth");

        return builder.build();
    }

    public List<RenderPipeline> getPreCompileList() {
        didPreCompile = true;
        return preCompileList;
    }

    public boolean isDidPreCompile() {
        return didPreCompile;
    }
}
