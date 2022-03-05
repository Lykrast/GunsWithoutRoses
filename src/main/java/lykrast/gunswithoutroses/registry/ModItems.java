package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import lykrast.gunswithoutroses.item.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GunsWithoutRoses.MODID)
public class ModItems {
	public static GunItem ironGun, goldGun, diamondShotgun, diamondSniper, diamondGatling;
	public static BulletItem flintBullet, ironBullet, blazeBullet, hungerBullet;

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		
		//I think maybe some mapping stuff failed on that 1.18.2 build... either way it was called "Ingredient.of" before
		ironGun = initItem(reg, new GunItem(defP().durability(513), 0, 1, 16, 1.5, 14).repair(() -> Ingredient.m_204132_(Tags.Items.INGOTS_IRON)), "iron_gun");
		goldGun = initItem(reg, new GunItem(defP().durability(104), 0, 1, 16, 1.5, 22).repair(() -> Ingredient.m_204132_(Tags.Items.INGOTS_GOLD)), "gold_gun");
		diamondShotgun = initItem(reg, new ShotgunItem(defP().durability(2076), 0, 0.45, 16, 6, 10, 5).ignoreInvulnerability(true).fireSound(ModSounds.shotgun).repair(() -> Ingredient.m_204132_(Tags.Items.GEMS_DIAMOND)), "diamond_shotgun");
		diamondSniper = initItem(reg, new GunItem(defP().durability(2076), 0, 1.6, 22, 0, 10).projectileSpeed(4).fireSound(ModSounds.sniper).repair(() -> Ingredient.m_204132_(Tags.Items.GEMS_DIAMOND)), "diamond_sniper");
		diamondGatling = initItem(reg, new GatlingItem(defP().durability(2076), 0, 1, 4, 4, 10).ignoreInvulnerability(true).repair(() -> Ingredient.m_204132_(Tags.Items.GEMS_DIAMOND)), "diamond_gatling");
		
		flintBullet = initItem(reg, new BulletItem(defP(), 5), "flint_bullet");
		ironBullet = initItem(reg, new BulletItem(defP(), 6), "iron_bullet");
		blazeBullet = initItem(reg, new BlazeBulletItem(defP(), 7), "blaze_bullet");
		hungerBullet = initItem(reg, new HungerBulletItem(defP().stacksTo(1), 5), "hunger_bullet");
	}

	public static Item.Properties defP() {
		return new Item.Properties().tab(ItemGroupGunsWithoutRoses.INSTANCE);
	}

	public static Item.Properties compat(String modid) {
		//The Team Abnormals way, works for now cause not using classes from other mods
		return new Item.Properties().tab(ModList.get().isLoaded(modid) ? ItemGroupGunsWithoutRoses.INSTANCE : null);
	}

	public static <I extends Item> I initItem(IForgeRegistry<Item> reg, I item, String name) {
		item.setRegistryName(GunsWithoutRoses.MODID, name);
		reg.register(item);
		return item;
	}
}
