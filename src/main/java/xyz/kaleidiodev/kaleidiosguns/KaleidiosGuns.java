package xyz.kaleidiodev.kaleidiosguns;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.kaleidiodev.kaleidiosguns.config.KGConfig;
import xyz.kaleidiodev.kaleidiosguns.event.ModTradesEvent;
import xyz.kaleidiodev.kaleidiosguns.event.ShootEvent;
import xyz.kaleidiodev.kaleidiosguns.item.GunItem;
import xyz.kaleidiodev.kaleidiosguns.network.CustomNetworkHandler;

@Mod("kaleidiosguns")
public class KaleidiosGuns {

	public static final String MODID = "kaleidiosguns";
	public static final Logger LOGGER = LogManager.getLogger();

	public static boolean VivecraftForgeExtensionPresent = false;

	public KaleidiosGuns() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, KGConfig.spec);
		loadConfigFile("kaleidiosguns-common.toml",KGConfig.spec);
		new CustomNetworkHandler().init();
		if (ModList.get().isLoaded("vivecraftforgeextensions")) {
			VivecraftForgeExtensionPresent = true;
			MinecraftForge.EVENT_BUS.register(ShootEvent.class);
		}
	}

	public static ResourceLocation rl(String name) {
		return new ResourceLocation(MODID, name);
	}

	public static void loadConfigFile(String fileName, ForgeConfigSpec targetSpec) {
		CommentedFileConfig replacementConfig = CommentedFileConfig
				.builder(FMLPaths.CONFIGDIR.get().resolve(fileName))
				.sync()
				.preserveInsertionOrder()
				.writingMode(WritingMode.REPLACE)
				.build();
		replacementConfig.load();
		replacementConfig.save();

		targetSpec.setConfig(replacementConfig);
	}
}
