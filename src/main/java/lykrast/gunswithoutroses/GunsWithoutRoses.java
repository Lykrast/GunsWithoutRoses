package lykrast.gunswithoutroses;

import lykrast.gunswithoutroses.config.GunsWithoutRosesConfig;
import lykrast.gunswithoutroses.network.CustomNetworkHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("gunswithoutroses")
public class GunsWithoutRoses {

	public static final String MODID = "gunswithoutroses";
	public static final Logger LOGGER = LogManager.getLogger();

	public GunsWithoutRoses() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, GunsWithoutRosesConfig.spec);
		new CustomNetworkHandler().init();
	}

	public static ResourceLocation rl(String name) {
		return new ResourceLocation(MODID, name);
	}
}
