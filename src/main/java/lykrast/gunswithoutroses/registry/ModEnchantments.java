package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import lykrast.gunswithoutroses.enchantment.GunEnchantment;
import lykrast.gunswithoutroses.item.GunItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GunsWithoutRoses.MODID)
public class ModEnchantments {
	public static Enchantment impact, bullseye, sleightOfHand, preserving;
	public static final EnchantmentCategory TYPE_GUN = EnchantmentCategory.create("GWR_GUN", (item) -> item instanceof GunItem);

	@SubscribeEvent
	public static void registerEnchantments(final RegistryEvent.Register<Enchantment> event) {
		IForgeRegistry<Enchantment> reg = event.getRegistry();
		
		impact = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.COMMON, 5, 1, 11, 20), "impact");
		bullseye = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.COMMON, 3, 5, 9, 15), "bullseye");
		sleightOfHand = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.UNCOMMON, 3, 12, 20, 40), "sleight_of_hand");
		preserving = initEnchant(reg, new GunEnchantment(Enchantment.Rarity.RARE, 3, 15, 9, 50), "preserving");
	}

	public static Enchantment initEnchant(IForgeRegistry<Enchantment> reg, Enchantment enchantment, String name) {
		enchantment.setRegistryName(GunsWithoutRoses.MODID, name);
		reg.register(enchantment);
		return enchantment;
	}
}
