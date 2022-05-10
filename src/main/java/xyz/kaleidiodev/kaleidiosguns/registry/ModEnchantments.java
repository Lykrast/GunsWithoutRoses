package xyz.kaleidiodev.kaleidiosguns.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.kaleidiodev.kaleidiosguns.KaleidiosGuns;
import xyz.kaleidiodev.kaleidiosguns.enchantment.GunEnchantment;
import xyz.kaleidiodev.kaleidiosguns.item.GunItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = KaleidiosGuns.MODID)
public class ModEnchantments {

	public static Enchantment impact, bullseye, sleightOfHand, preserving, accelerator;
	public static final EnchantmentType TYPE_GUN = EnchantmentType.create("GWR_GUN", (item) -> item instanceof GunItem);

	@SubscribeEvent
	public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
		IForgeRegistry<Enchantment> reg = event.getRegistry();

		impact = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.COMMON, 5, 1, 11, 20), "impact");
		bullseye = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.COMMON, 3, 5, 9, 15), "bullseye");
		sleightOfHand = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.UNCOMMON, 2, 12, 20, 40), "sleight_of_hand");
		preserving = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.RARE, 3, 15, 9, 50), "preserving");
		accelerator = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.UNCOMMON, 3, 10, 15, 35), "accelerator");
	}

	public static Enchantment initEnchant(IForgeRegistry<Enchantment> reg, Enchantment enchantment, String name) {
		enchantment.setRegistryName(KaleidiosGuns.MODID, name);
		reg.register(enchantment);
		return enchantment;
	}
}
