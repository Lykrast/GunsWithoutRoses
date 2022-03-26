package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import lykrast.gunswithoutroses.config.GunsWithoutRosesConfig;
import lykrast.gunswithoutroses.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GunsWithoutRoses.MODID)
public class ModItems {

	public static GunItem
			ironGun,
			goldGun,
			diamondShotgun,
			doubleBarrelShotgun,
			carbineSniper,
			diamondSniper,
			diamondGatling,
			smgGatling,
			streamGatling,
			assaultGatling;

	public static BulletItem flintBullet, ironBullet, blazeBullet, hungerBullet;

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();

		// Pistols
		ironGun = initItem(reg, new GunItem(defP().durability(513), 0, GunsWithoutRosesConfig.ironDamageMultiplier.get(), GunsWithoutRosesConfig.ironFireDelay.get(), 5, 14).repair(() -> Ingredient.of(Tags.Items.INGOTS_IRON)), "iron_gun").projectileSpeed(4);
		goldGun = initItem(reg, new GunItem(defP().durability(104), 0, GunsWithoutRosesConfig.goldDamageMultiplier.get(), GunsWithoutRosesConfig.goldFireDelay.get(), 5, 22).repair(() -> Ingredient.of(Tags.Items.INGOTS_GOLD)), "gold_gun").projectileSpeed(4);

		// Shotguns
		diamondShotgun = initItem(reg, new ShotgunItem(defP().durability(2076), 0, GunsWithoutRosesConfig.diamondShotgunDamageMultiplier.get(), GunsWithoutRosesConfig.diamondShotgunFireDelay.get(), 6, 10, 5).ignoreInvulnerability(true).fireSound(ModSounds.shotgun).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "diamond_shotgun").projectileSpeed(16);
		doubleBarrelShotgun = initItem(reg, new ShotgunItem(defP().durability(2076), 0, GunsWithoutRosesConfig.diamondDoubleShotgunDamageMultiplier.get(), GunsWithoutRosesConfig.diamondDoubleShotgunFireDelay.get(), 20, 10, 10).ignoreInvulnerability(true).fireSound(ModSounds.shotgun).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "double_barrel_shotgun").projectileSpeed(16);

		// Snipers
		diamondSniper = initItem(reg, new GunItem(defP().durability(2076), 0, GunsWithoutRosesConfig.diamondSniperDamageMultiplier.get(), GunsWithoutRosesConfig.diamondSniperFireDelay.get(), 0, 10).projectileSpeed(32).fireSound(ModSounds.sniper).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "diamond_sniper");
		carbineSniper = initItem(reg, new GunItem(defP().durability(2076), 0, GunsWithoutRosesConfig.diamondCarbineDamageMultiplier.get(), GunsWithoutRosesConfig.diamondCarbineFireDelay.get(), 0, 10).projectileSpeed(32).fireSound(ModSounds.sniper).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "carbine_sniper");

		// Gatlings
		diamondGatling = initItem(reg, new GatlingItem(defP().durability(2076), 0, GunsWithoutRosesConfig.diamondGatlingDamageMultiplier.get(), GunsWithoutRosesConfig.diamondGatlingFireDelay.get(), 4, 10).ignoreInvulnerability(true).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "diamond_gatling").projectileSpeed(16);
		smgGatling = initItem(reg, new GatlingItem(defP().durability(2076), 0, GunsWithoutRosesConfig.diamondSmgDamageMultiplier.get(), GunsWithoutRosesConfig.diamondSmgFireDelay.get(), 15, 10).ignoreInvulnerability(true).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "smg_gatling").projectileSpeed(16);
		streamGatling = initItem(reg, new GatlingItem(defP().durability(2076), 0, GunsWithoutRosesConfig.diamondStreamGatlingDamageMultiplier.get(), GunsWithoutRosesConfig.diamondStreamGatlingFireDelay.get(), 5, 10).ignoreInvulnerability(true).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "stream_gatling").projectileSpeed(3);
		assaultGatling = initItem(reg, new GatlingItem(defP().durability(2076), 0, GunsWithoutRosesConfig.diamondAssaultDamageMultiplier.get(), GunsWithoutRosesConfig.diamondAssaultFireDelay.get(), 10, 10).ignoreInvulnerability(true).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "assault_gatling").projectileSpeed(16);

        // Bullets
		flintBullet = initItem(reg, new BulletItem(defP(), GunsWithoutRosesConfig.flintBulletDamage.get()), "flint_bullet");
		ironBullet = initItem(reg, new BulletItem(defP(), GunsWithoutRosesConfig.ironBulletDamage.get()), "iron_bullet");
		blazeBullet = initItem(reg, new BlazeBulletItem(defP(), GunsWithoutRosesConfig.blazeBulletDamage.get()), "blaze_bullet");
		hungerBullet = initItem(reg, new HungerBulletItem(defP().stacksTo(1), GunsWithoutRosesConfig.hungerBulletDamage.get()), "hunger_bullet");
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
