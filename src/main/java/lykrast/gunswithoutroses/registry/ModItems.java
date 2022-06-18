package lykrast.gunswithoutroses.registry;

import java.util.function.Supplier;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import lykrast.gunswithoutroses.item.BlazeBulletItem;
import lykrast.gunswithoutroses.item.BulletItem;
import lykrast.gunswithoutroses.item.GatlingItem;
import lykrast.gunswithoutroses.item.GunItem;
import lykrast.gunswithoutroses.item.HungerBulletItem;
import lykrast.gunswithoutroses.item.ShotgunItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static RegistryObject<GunItem> ironGun, goldGun, diamondShotgun, diamondSniper, diamondGatling;
	public static RegistryObject<BulletItem> flintBullet, ironBullet, blazeBullet, hungerBullet;
	public static final DeferredRegister<Item> REG = DeferredRegister.create(ForgeRegistries.ITEMS, GunsWithoutRoses.MODID);

	static {		
		ironGun = initItem(() -> new GunItem(defP().durability(513), 0, 1, 16, 1.5, 14).repair(() -> Ingredient.of(Tags.Items.INGOTS_IRON)), "iron_gun");
		goldGun = initItem(() -> new GunItem(defP().durability(104), 0, 1, 16, 1.5, 22).repair(() -> Ingredient.of(Tags.Items.INGOTS_GOLD)), "gold_gun");
		diamondShotgun = initItem(() -> new ShotgunItem(defP().durability(2076), 0, 0.45, 16, 6, 10, 5).ignoreInvulnerability(true).fireSound(ModSounds.shotgun::get).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "diamond_shotgun");
		diamondSniper = initItem(() -> new GunItem(defP().durability(2076), 0, 1.6, 22, 0, 10).projectileSpeed(4).fireSound(ModSounds.sniper::get).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "diamond_sniper");
		diamondGatling = initItem(() -> new GatlingItem(defP().durability(2076), 0, 1, 4, 4, 10).ignoreInvulnerability(true).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "diamond_gatling");
		
		flintBullet = initItem(() -> new BulletItem(defP(), 5), "flint_bullet");
		ironBullet = initItem(() -> new BulletItem(defP(), 6), "iron_bullet");
		blazeBullet = initItem(() -> new BlazeBulletItem(defP(), 8), "blaze_bullet");
		hungerBullet = initItem(() -> new HungerBulletItem(defP().stacksTo(1), 5), "hunger_bullet");
	}

	public static Item.Properties defP() {
		return new Item.Properties().tab(ItemGroupGunsWithoutRoses.INSTANCE);
	}

	//Oh hey there is a method to cast the registry objects! Neat!
	public static <I extends Item> RegistryObject<I> initItem(Supplier<I> item, String name) {
		REG.register(name, item);
		return RegistryObject.create(GunsWithoutRoses.rl(name), ForgeRegistries.ITEMS);
	}
}
