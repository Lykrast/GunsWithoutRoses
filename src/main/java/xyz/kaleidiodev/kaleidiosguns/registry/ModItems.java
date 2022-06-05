package xyz.kaleidiodev.kaleidiosguns.registry;

import net.minecraft.item.Item;
import net.minecraft.item.KnowledgeBookItem;
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
		ironGun = initItem(reg, new GunItem(defP().durability(KGConfig.ironPistolDurability.get()), 0, KGConfig.ironPistolDamageMultiplier.get(), KGConfig.ironPistolFireDelay.get(), KGConfig.ironPistolInaccuracy.get(), KGConfig.ironPistolEnchantability.get()).repair(() -> Ingredient.of(Tags.Items.INGOTS_IRON)), "pistol").projectileSpeed(KGConfig.ironPistolProjectileSpeed.get()).fireSound(ModSounds.pistol);

		// Shotguns
		diamondShotgun = initItem(reg, new ShotgunItem(defP().durability(KGConfig.diamondShotgunDurability.get()), 0, KGConfig.diamondShotgunDamageMultiplier.get(), KGConfig.diamondShotgunFireDelay.get(), KGConfig.diamondShotgunInaccuracy.get(), KGConfig.diamondShotgunEnchantability.get(), KGConfig.diamondShotgunBulletCount.get()).fireSound(ModSounds.shotgun).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "shotgun").projectileSpeed(KGConfig.diamondShotgunProjectileSpeed.get());
		doubleBarrelShotgun = initItem(reg, new ShotgunItem(defP().durability(KGConfig.goldDoubleShotgunDurability.get()), 0, KGConfig.goldDoubleShotgunDamageMultiplier.get(), KGConfig.goldDoubleShotgunFireDelay.get(), KGConfig.goldDoubleShotgunInaccuracy.get(), KGConfig.goldDoubleShotgunEnchantability.get(), KGConfig.goldDoubleShotgunBulletCount.get()).fireSound(ModSounds.double_shotgun).repair(() -> Ingredient.of(Tags.Items.INGOTS_GOLD)).doubleBarrel(true), "double_barrel_shotgun").projectileSpeed(KGConfig.goldDoubleShotgunProjectileSpeed.get());

		// Snipers
		diamondSniper = initItem(reg, new GunItem(defP().durability(KGConfig.diamondSniperDurability.get()), 0, KGConfig.diamondSniperDamageMultiplier.get(), KGConfig.diamondSniperFireDelay.get(), KGConfig.diamondSniperInaccuracy.get(), KGConfig.diamondSniperEnchantability.get()).projectileSpeed(KGConfig.diamondSniperProjectileSpeed.get()).fireSound(ModSounds.sniper).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "sniper").ignoreInvulnerability(false).collateral(true);
		carbineSniper = initItem(reg, new GunItem(defP().durability(KGConfig.ironCarbineDurability.get()), 0, KGConfig.ironCarbineDamageMultiplier.get(), KGConfig.ironCarbineFireDelay.get(), KGConfig.ironCarbineInaccuracy.get(), KGConfig.ironCarbineEnchantability.get()).projectileSpeed(KGConfig.ironCarbineProjectileSpeed.get()).fireSound(ModSounds.carbine).repair(() -> Ingredient.of(Tags.Items.INGOTS_IRON)), "carbine");

		// Gatlings
		smgGatling = initItem(reg, new GatlingItem(defP().durability(KGConfig.diamondSmgDurability.get()), 0, KGConfig.diamondSmgDamageMultiplier.get(), KGConfig.diamondSmgFireDelay.get(), KGConfig.diamondSmgInaccuracy.get(), KGConfig.diamondSmgEnchantability.get()).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "minegun").projectileSpeed(KGConfig.diamondSmgProjectileSpeed.get()).canMineBlocks(true);
		streamGatling = initItem(reg, new GatlingItem(defP().durability(KGConfig.goldStreamDurability.get()), 0, KGConfig.goldStreamDamageMultiplier.get(), KGConfig.goldStreamFireDelay.get(), KGConfig.goldStreamInaccuracy.get(), KGConfig.goldStreamEnchantability.get()).repair(() -> Ingredient.of(Tags.Items.INGOTS_GOLD)), "stream_rifle").projectileSpeed(KGConfig.goldStreamProjectileSpeed.get()).fireSound(ModSounds.stream_rifle);
		assaultGatling = initItem(reg, new GatlingItem(defP().durability(KGConfig.ironAssaultDurability.get()), 0, KGConfig.ironAssaultDamageMultiplier.get(), KGConfig.ironAssaultFireDelay.get(), KGConfig.ironAssaultInaccuracy.get(), KGConfig.ironAssaultEnchantability.get()).repair(() -> Ingredient.of(Tags.Items.INGOTS_IRON)), "assault_rifle").projectileSpeed(KGConfig.ironAssaultProjectileSpeed.get()).fireSound(ModSounds.smg);

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
