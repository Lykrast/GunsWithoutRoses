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

	public static Enchantment impact, bullseye, sleightOfHand, preserving, accelerator, division, passionForBlood, marker, luckyShot, cowboy, maneuvering, frostShot, counterStrike;
	public static final EnchantmentType TYPE_GUN = EnchantmentType.create("GWR_GUN", (item) -> item instanceof GunItem);

	@SubscribeEvent
	public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
		IForgeRegistry<Enchantment> reg = event.getRegistry();

		impact = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.COMMON, 5, 4, 8, 20, TYPE_GUN), "impact");
		bullseye = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.COMMON, 3, 4, 8, 20, TYPE_GUN), "bullseye");
		sleightOfHand = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.UNCOMMON, 2, 6, 12, 30, TYPE_GUN), "sleight_of_hand");
		preserving = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.RARE, 3, 5, 10, 30, TYPE_GUN), "preserving");
		accelerator = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.COMMON, 3, 3, 6, 20, TYPE_GUN), "accelerator");
		division = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.COMMON, 4, 4, 8, 20, TYPE_GUN), "division");
		passionForBlood = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.RARE, 2, 8, 16, 25, TYPE_GUN), "passion_for_blood");
		marker = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.RARE, 1, 7, 14, 25, TYPE_GUN), "marker");
		luckyShot = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.UNCOMMON, 3, 6, 12, 30, TYPE_GUN), "lucky_shot");
		cowboy = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.UNCOMMON, 1, 9, 18, 25, TYPE_GUN), "cowboy");
		maneuvering = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.RARE, 1, 10, 20, 30, TYPE_GUN), "maneuvering");
		frostShot = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.UNCOMMON, 3, 5, 10, 30, TYPE_GUN), "frost_shard");
		counterStrike = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.UNCOMMON, 1, 12, 24, 30, TYPE_GUN), "counter_strike");
	}

	public static Enchantment initEnchant(IForgeRegistry<Enchantment> reg, Enchantment enchantment, String name) {
		enchantment.setRegistryName(KaleidiosGuns.MODID, name);
		reg.register(enchantment);
		return enchantment;
	}
}
