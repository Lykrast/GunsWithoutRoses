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
		ironGun = initItem(reg, new GunItem(defP().durability(KGConfig.ironDurability.get()), 0, KGConfig.ironDamageMultiplier.get(), KGConfig.ironFireDelay.get(), KGConfig.ironInaccuracy.get(), KGConfig.ironEnchantability.get()).repair(() -> Ingredient.of(Tags.Items.INGOTS_IRON)), "pistol").projectileSpeed(12).fireSound(ModSounds.pistol);

		// Shotguns
		diamondShotgun = initItem(reg, new ShotgunItem(defP().durability(KGConfig.diamondShotgunDurability.get()), 0, KGConfig.diamondShotgunDamageMultiplier.get(), KGConfig.diamondShotgunFireDelay.get(), KGConfig.diamondShotgunInaccuracy.get(), KGConfig.diamondShotgunEnchantability.get(), 5).fireSound(ModSounds.shotgun).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "shotgun").projectileSpeed(12);
		doubleBarrelShotgun = initItem(reg, new ShotgunItem(defP().durability(KGConfig.diamondDoubleShotgunDurability.get()), 0, KGConfig.diamondDoubleShotgunDamageMultiplier.get(), KGConfig.diamondDoubleShotgunFireDelay.get(), KGConfig.diamondDoubleShotgunInaccuracy.get(), KGConfig.diamondDoubleShotgunEnchantability.get(), 10).fireSound(ModSounds.double_shotgun).repair(() -> Ingredient.of(Tags.Items.INGOTS_GOLD)), "double_barrel_shotgun").projectileSpeed(12);

		// Snipers
		diamondSniper = initItem(reg, new GunItem(defP().durability(KGConfig.diamondSniperDurability.get()), 0, KGConfig.diamondSniperDamageMultiplier.get(), KGConfig.diamondSniperFireDelay.get(), KGConfig.diamondSniperInaccuracy.get(), KGConfig.diamondSniperEnchantability.get()).projectileSpeed(24).fireSound(ModSounds.sniper).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "sniper");
		carbineSniper = initItem(reg, new GunItem(defP().durability(KGConfig.diamondCarbineDurability.get()), 0, KGConfig.diamondCarbineDamageMultiplier.get(), KGConfig.diamondCarbineFireDelay.get(), KGConfig.diamondCarbineInaccuracy.get(), KGConfig.diamondCarbineEnchantability.get()).projectileSpeed(12).fireSound(ModSounds.carbine).repair(() -> Ingredient.of(Tags.Items.INGOTS_IRON)), "carbine");

		// Gatlings
		smgGatling = initItem(reg, new GatlingItem(defP().durability(KGConfig.diamondSmgDurability.get()), 0, KGConfig.diamondSmgDamageMultiplier.get(), KGConfig.diamondSmgFireDelay.get(), KGConfig.diamondSmgInaccuracy.get(), KGConfig.diamondSmgEnchantability.get()).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "machine_pistol").projectileSpeed(12);
		streamGatling = initItem(reg, new GatlingItem(defP().durability(KGConfig.diamondStreamDurability.get()), 0, KGConfig.diamondStreamGatlingDamageMultiplier.get(), KGConfig.diamondStreamGatlingFireDelay.get(), KGConfig.diamondStreamInaccuracy.get(), KGConfig.diamondStreamEnchantability.get()).repair(() -> Ingredient.of(Tags.Items.INGOTS_GOLD)), "stream_rifle").projectileSpeed(4).fireSound(ModSounds.stream_rifle);
		assaultGatling = initItem(reg, new GatlingItem(defP().durability(KGConfig.diamondAssaultDurability.get()), 0, KGConfig.diamondAssaultDamageMultiplier.get(), KGConfig.diamondAssaultFireDelay.get(), KGConfig.diamondAssaultInaccuracy.get(), KGConfig.diamondAssaultEnchantability.get()).repair(() -> Ingredient.of(Tags.Items.INGOTS_IRON)), "assault_rifle").projectileSpeed(12).fireSound(ModSounds.smg);

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
