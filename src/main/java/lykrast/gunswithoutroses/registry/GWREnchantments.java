package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import lykrast.gunswithoutroses.enchantment.GunEnchantment;
import lykrast.gunswithoutroses.item.GunItem;
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
}
