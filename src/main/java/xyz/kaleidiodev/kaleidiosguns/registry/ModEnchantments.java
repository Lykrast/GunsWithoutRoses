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
import xyz.kaleidiodev.kaleidiosguns.item.ShotgunItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = KaleidiosGuns.MODID)
public class ModEnchantments {

	public static Enchantment impact, bullseye, sleightOfHand, preserving, accelerator, division, passionForBlood;
	public static final EnchantmentType TYPE_GUN = EnchantmentType.create("GWR_GUN", (item) -> item instanceof GunItem);

	@SubscribeEvent
	public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
		IForgeRegistry<Enchantment> reg = event.getRegistry();

		impact = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.COMMON, 5, 3, 11, 20, TYPE_GUN), "impact");
		bullseye = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.COMMON, 3, 5, 9, 15, TYPE_GUN), "bullseye");
		sleightOfHand = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.UNCOMMON, 2, 12, 15, 40, TYPE_GUN), "sleight_of_hand");
		preserving = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.RARE, 3, 15, 9, 50, TYPE_GUN), "preserving");
		accelerator = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.COMMON, 3, 10, 15, 35, TYPE_GUN), "accelerator");
		division = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.UNCOMMON, 4, 8, 15, 25, TYPE_GUN), "division");
		passionForBlood = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.RARE, 2, 25, 20, 50, TYPE_GUN), "passion_for_blood");
	}

	public static Enchantment initEnchant(IForgeRegistry<Enchantment> reg, Enchantment enchantment, String name) {
		enchantment.setRegistryName(KaleidiosGuns.MODID, name);
		reg.register(enchantment);
		return enchantment;
	}
}
