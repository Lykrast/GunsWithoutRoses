package lykrast.gunswithoutroses.config;

import net.minecraftforge.fml.config.ModConfig;

public class GWRConfigValues {
	public static boolean ALLOW_PUNCH = false;

	public static void refresh(ModConfig config) {
		ALLOW_PUNCH = GWRConfig.COMMON.allowPunch.get();
	}
}
