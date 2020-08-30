package lykrast.gunswithoutroses;

import lykrast.gunswithoutroses.item.*;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GunsWithoutRoses.MODID)
public class ModItems {
	public static GunItem ironGun, goldGun, diamondShotgun, diamondSniper, diamondGatling;
	public static BulletItem flintBullet, ironBullet, blazeBullet, hungerBullet;

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		
		ironGun = initItem(reg, new GunItem(defP().maxDamage(813), 0, 1, 16, 1.5, false, 14), "iron_gun");
		goldGun = initItem(reg, new GunItem(defP().maxDamage(104), 0, 1, 16, 1.5, false, 22), "gold_gun");
		diamondShotgun = initItem(reg, new ShotgunItem(defP().maxDamage(5076), 0, 1/3.0, 16, 6, true, 10, 6), "diamond_shotgun");
		diamondSniper = initItem(reg, new GunItem(defP().maxDamage(5076), 0, 2.5, 22, 0, false, 10), "diamond_sniper");
		//That one doesn't feel good at all, needs more adjustments
		diamondGatling = initItem(reg, new GunItem(defP().maxDamage(5076), 0, 0.75, 5, 4, true, 10), "diamond_gatling");
		
		flintBullet = initItem(reg, new BulletItem(defP(), 5), "flint_bullet");
		ironBullet = initItem(reg, new BulletItem(defP(), 6), "iron_bullet");
		blazeBullet = initItem(reg, new BlazeBulletItem(defP(), 7), "blaze_bullet");
		hungerBullet = initItem(reg, new HungerBulletItem(defP().maxStackSize(1), 5), "hunger_bullet");
	}

	public static Item.Properties defP() {
		return new Item.Properties().group(ItemGroupGunsWithoutRoses.INSTANCE);
	}

	public static <I extends Item> I initItem(IForgeRegistry<Item> reg, I item, String name) {
		item.setRegistryName(GunsWithoutRoses.MODID, name);
		reg.register(item);
		return item;
	}
}
