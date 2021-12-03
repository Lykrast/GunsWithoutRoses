package lykrast.gunswithoutroses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod("gunswithoutroses")
public class GunsWithoutRoses {
	public static final String MODID = "gunswithoutroses";
	
	public static final Logger LOGGER = LogManager.getLogger();

	public GunsWithoutRoses() {
		//Configs one day
	}
	
	public static ResourceLocation rl(String name) {
		return new ResourceLocation(MODID, name);
	}
}
