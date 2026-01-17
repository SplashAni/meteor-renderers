package meteor.renderers;


import com.mojang.logging.LogUtils;
import meteor.renderers.managers.ShaderManager;
import meteor.renderers.managers.ShaderPipelinesManager;
import meteor.renderers.modules.EntityShader;
import meteor.renderers.modules.HandShader;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.item.Items;
import org.slf4j.Logger;

public class Main extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category RENDERER_CATEGORY = new Category("Renderer", Items.SPYGLASS.getDefaultStack());

    public static ShaderPipelinesManager SHADER_PIPELINE_MANAGER = new ShaderPipelinesManager();
    public static ShaderManager SHADER_MANAGER;
    @Override
    public void onInitialize() {


        Modules.get().add(new HandShader());
        Modules.get().add(new EntityShader());

    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(RENDERER_CATEGORY);
    }

    @Override
    public String getPackage() {
        return "meteor.renderers";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("SplashAni", "meteor-renderers");
    }
}
