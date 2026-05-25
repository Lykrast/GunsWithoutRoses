package lykrast.gunswithoutroses.config;

import net.minecraftforge.fml.config.ModConfig;

public class GWRConfigValues {
	public static float GUN_KNOCKBACK = 0.1f;
	public static boolean ALLOW_PUNCH = false;

	public static void refresh(ModConfig config) {
		GUN_KNOCKBACK = GWRConfig.COMMON.gunKnockback.get().floatValue();
		ALLOW_PUNCH = GWRConfig.COMMON.allowPunch.get();
	}
}
