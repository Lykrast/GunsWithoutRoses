package xyz.kaleidiodev.kaleidiosguns.registry;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.kaleidiodev.kaleidiosguns.KaleidiosGuns;
import xyz.kaleidiodev.kaleidiosguns.config.KGConfig;
import xyz.kaleidiodev.kaleidiosguns.item.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = KaleidiosGuns.MODID)
public class ModItems {

	public static GunItem
			ironGun,
			diamondShotgun,
			doubleBarrelShotgun,
			carbineSniper,
			diamondSniper,
			smgGatling,
			streamGatling,
			assaultGatling;

	public static BulletItem flintBullet, ironBullet, blazeBullet, hungerBullet;

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();

		// Pistols
		ironGun = initItem(reg, new GunItem(defP().durability(1000), 0, KGConfig.ironDamageMultiplier.get(), KGConfig.ironFireDelay.get(), 5, 14).repair(() -> Ingredient.of(Tags.Items.INGOTS_IRON)), "pistol").projectileSpeed(16);

		// Shotguns
		diamondShotgun = initItem(reg, new ShotgunItem(defP().durability(2000), 0, KGConfig.diamondShotgunDamageMultiplier.get(), KGConfig.diamondShotgunFireDelay.get(), 5, 10, 5).ignoreInvulnerability(true).fireSound(ModSounds.shotgun).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "shotgun").projectileSpeed(16);
		doubleBarrelShotgun = initItem(reg, new ShotgunItem(defP().durability(500), 0, KGConfig.diamondDoubleShotgunDamageMultiplier.get(), KGConfig.diamondDoubleShotgunFireDelay.get(), 10, 23, 10).ignoreInvulnerability(true).fireSound(ModSounds.shotgun).repair(() -> Ingredient.of(Tags.Items.INGOTS_GOLD)), "double_barrel_shotgun").projectileSpeed(16);

		// Snipers
		diamondSniper = initItem(reg, new GunItem(defP().durability(2000), 0, KGConfig.diamondSniperDamageMultiplier.get(), KGConfig.diamondSniperFireDelay.get(), 0, 10).projectileSpeed(32).fireSound(ModSounds.sniper).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "sniper");
		carbineSniper = initItem(reg, new GunItem(defP().durability(1000), 0, KGConfig.diamondCarbineDamageMultiplier.get(), KGConfig.diamondCarbineFireDelay.get(), 0, 14).projectileSpeed(16).fireSound(ModSounds.sniper).repair(() -> Ingredient.of(Tags.Items.INGOTS_IRON)), "carbine");

		// Gatlings
		smgGatling = initItem(reg, new GatlingItem(defP().durability(2000), 0, KGConfig.diamondSmgDamageMultiplier.get(), KGConfig.diamondSmgFireDelay.get(), 10, 10).ignoreInvulnerability(true).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "machine_pistol").projectileSpeed(16);
		streamGatling = initItem(reg, new GatlingItem(defP().durability(500), 0, KGConfig.diamondStreamGatlingDamageMultiplier.get(), KGConfig.diamondStreamGatlingFireDelay.get(), 0, 23).ignoreInvulnerability(true).repair(() -> Ingredient.of(Tags.Items.INGOTS_GOLD)), "stream_rifle").projectileSpeed(4);
		assaultGatling = initItem(reg, new GatlingItem(defP().durability(1000), 0, KGConfig.diamondAssaultDamageMultiplier.get(), KGConfig.diamondAssaultFireDelay.get(), 5, 14).ignoreInvulnerability(true).repair(() -> Ingredient.of(Tags.Items.INGOTS_IRON)), "assault_rifle").projectileSpeed(16);

        // Bullets
		flintBullet = initItem(reg, new BulletItem(defP(), KGConfig.flintBulletDamage.get()), "flint_bullet");
		ironBullet = initItem(reg, new BulletItem(defP(), KGConfig.ironBulletDamage.get()), "iron_bullet");
		blazeBullet = initItem(reg, new BlazeBulletItem(defP(), KGConfig.blazeBulletDamage.get()), "blaze_bullet");
		hungerBullet = initItem(reg, new HungerBulletItem(defP().stacksTo(1), KGConfig.hungerBulletDamage.get()), "hunger_bullet");
	}

	public static Item.Properties defP() {
		return new Item.Properties().tab(ItemGroupGuns.INSTANCE);
	}

	public static <I extends Item> I initItem(IForgeRegistry<Item> reg, I item, String name) {
		item.setRegistryName(KaleidiosGuns.MODID, name);
		reg.register(item);
		return item;
	}
}