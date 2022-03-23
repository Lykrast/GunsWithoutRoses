package lykrast.gunswithoutroses;

import lykrast.gunswithoutroses.config.GunsWithoutRosesConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod("gunswithoutroses")
public class GunsWithoutRoses {

	public static final String MODID = "gunswithoutroses";
	public static final Logger LOGGER = LogManager.getLogger();

	public GunsWithoutRoses() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, GunsWithoutRosesConfig.spec);
	}

	public static ResourceLocation rl(String name) {
		return new ResourceLocation(MODID, name);
	}
}
