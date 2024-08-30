package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import lykrast.gunswithoutroses.enchantment.GunEnchantment;
import lykrast.gunswithoutroses.item.GunItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GWREnchantments {
	public static RegistryObject<Enchantment> impact, bullseye, sleightOfHand, preserving;
	public static final EnchantmentCategory TYPE_GUN = EnchantmentCategory.create("GWR_GUN", (item) -> item instanceof GunItem);
	public static final DeferredRegister<Enchantment> REG = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, GunsWithoutRoses.MODID);

	static {
		impact = REG.register("impact", () -> new GunEnchantment(Enchantment.Rarity.COMMON, 5, 1, 11, 20));
		bullseye = REG.register("bullseye", () -> new GunEnchantment(Enchantment.Rarity.COMMON, 3, 5, 9, 15));
		sleightOfHand = REG.register("sleight_of_hand", () -> new GunEnchantment(Enchantment.Rarity.UNCOMMON, 3, 12, 20, 40));
		preserving = REG.register("preserving", () -> new GunEnchantment(Enchantment.Rarity.RARE, 3, 15, 9, 50));
	}
	
	/**
	 * Bonus base damage for Impact.
	 */
	public static final double impactBonus(int level) {
		return 0.5 * (level + 1);
	}
	
	/**
	 * Modify spread given Bullseye.
	 */
	public static final double bullseyeModify(int level, double spread) {
		return spread / (level + 1.0);
	}
	
	/**
	 * Modify fire delay given Sleight of Hand.
	 */
	public static final int sleightModify(int level, int fireDelay) {
		return (int)(fireDelay / (1 + 0.16*level));
	}
	
	/**
	 * Roll the chance for Preserving to save ammo.
	 */
	public static final boolean rollPreserving(int level, RandomSource random) {
		return random.nextInt(level + 2) >= 2;
	}
	
	/**
	 * Multiplier to chance to consume ammo from Preserving. Only for tooltip.
	 */
	public static final double preservingInverse(int level) {
		return 2.0/(level + 2);
	}
}
