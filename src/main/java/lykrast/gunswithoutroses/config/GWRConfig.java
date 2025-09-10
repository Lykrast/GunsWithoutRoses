package lykrast.gunswithoutroses.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class GWRConfig {
	//I think that's like the 4th time I copy paste this thing around?
	//I'm not making the individual guns/bullets configurable I don't like doing that and that's too much refactoring
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final GWRConfig COMMON;

	static {
		Pair<GWRConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(GWRConfig::new);
		COMMON = specPair.getLeft();
		COMMON_SPEC = specPair.getRight();
	}

	public final BooleanValue allowPunch;

	public GWRConfig(ForgeConfigSpec.Builder builder) {
		builder.comment("Enchantments");
		builder.push("enchantment");
		allowPunch = boolval(builder, "allowPunch", false, "Allow Punch (the vanilla Bow enchantment) to be put on guns for bonus knockback",
				"Disabling this only disables applying the enchant, it'll stay on guns already enchanted and keep working",
				"I think as an enchantment it's a bit too strong (I have more isolated knockback sources in addons), but it is very funny");
		builder.pop();
	}

	@SuppressWarnings("unused")
	private IntValue intval(ForgeConfigSpec.Builder builder, String name, int def, int min, int max, String... comments) {
		return builder.translation(name).comment(comments).comment("Default: " + def).defineInRange(name, def, min, max);
	}

	@SuppressWarnings("unused")
	private DoubleValue doubleval(ForgeConfigSpec.Builder builder, String name, double def, double min, double max, String... comments) {
		return builder.translation(name).comment(comments).comment("Default: " + def).defineInRange(name, def, min, max);
	}

	private BooleanValue boolval(ForgeConfigSpec.Builder builder, String name, boolean def, String... comments) {
		return builder.translation(name).comment(comments).comment("Default: " + def).define(name, def);
	}

}
