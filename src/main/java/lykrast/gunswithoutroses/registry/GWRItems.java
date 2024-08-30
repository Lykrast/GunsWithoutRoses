package lykrast.gunswithoutroses.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import lykrast.gunswithoutroses.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

public class GWRItems {
	public static RegistryObject<GunItem> ironGun, goldGun, diamondShotgun, diamondSniper, diamondGatling;
	public static RegistryObject<BulletItem> flintBullet, ironBullet, blazeBullet;
	public static final DeferredRegister<Item> REG = DeferredRegister.create(ForgeRegistries.ITEMS, GunsWithoutRoses.MODID);
	
	private static List<RegistryObject<? extends Item>> orderedItemsCreative = new ArrayList<>();

	public static void makeCreativeTab(RegisterEvent event) {
		event.register(Registries.CREATIVE_MODE_TAB, helper -> {
			helper.register(ResourceKey.create(Registries.CREATIVE_MODE_TAB, GunsWithoutRoses.rl("gunswithoutroses")),
					CreativeModeTab.builder().title(Component.translatable("itemGroup.gunswithoutroses")).icon(() -> new ItemStack(ironGun.get()))
							.displayItems((parameters, output) -> orderedItemsCreative.forEach(i -> output.accept(i.get()))).build());
		});
	}

	static {
		ironGun = initItem(() -> new GunItem(defP().durability(513), 0, 1, 16, 1.5, 14).repair(() -> Ingredient.of(Tags.Items.INGOTS_IRON)), "iron_gun");
		goldGun = initItem(() -> new GunItem(defP().durability(104), 0, 1, 16, 1.5, 22).repair(() -> Ingredient.of(Tags.Items.INGOTS_GOLD)), "gold_gun");
		diamondShotgun = initItem(() -> new ShotgunItem(defP().durability(2076), 0, 0.45, 16, 6, 10, 5).fireSound(GWRSounds.shotgun::get).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "diamond_shotgun");
		diamondSniper = initItem(() -> new GunItem(defP().durability(2076), 0, 1.6, 22, 0, 10).projectileSpeed(4).fireSound(GWRSounds.sniper::get).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "diamond_sniper");
		diamondGatling = initItem(() -> new GatlingItem(defP().durability(2076), 0, 1, 4, 4, 10).repair(() -> Ingredient.of(Tags.Items.GEMS_DIAMOND)), "diamond_gatling");
		
		flintBullet = initItem(() -> new BulletItem(defP(), 5), "flint_bullet");
		ironBullet = initItem(() -> new BulletItem(defP(), 6), "iron_bullet");
		blazeBullet = initItem(() -> new BlazeBulletItem(defP(), 8), "blaze_bullet");
	}

	public static Item.Properties defP() {
		return new Item.Properties();
	}

	//Oh hey there is a method to cast the registry objects! Neat!
	public static <I extends Item> RegistryObject<I> initItem(Supplier<I> item, String name) {
		REG.register(name, item);
		RegistryObject<I> rego = RegistryObject.create(GunsWithoutRoses.rl(name), ForgeRegistries.ITEMS);
		orderedItemsCreative.add(rego);
		return rego;
	}
}
