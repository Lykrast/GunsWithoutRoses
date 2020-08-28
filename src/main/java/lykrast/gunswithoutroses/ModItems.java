package lykrast.gunswithoutroses;

import lykrast.gunswithoutroses.item.*;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GunsWithoutRoses.MODID)
public class ModItems {
	public static GunItem ironGun, goldGun;
	public static BulletItem flintBullet, ironBullet, blazeBullet, hungerBullet;

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		
		ironGun = initItem(reg, new GunItem(defP().maxDamage(813), 16, 1.5, 14), "iron_gun");
		goldGun = initItem(reg, new GunItem(defP().maxDamage(104), 16, 1.5, 22), "gold_gun");
		
		flintBullet = initItem(reg, new BulletItem(defP(), 5), "flint_bullet");
		ironBullet = initItem(reg, new BulletItem(defP(), 6), "iron_bullet");
		blazeBullet = initItem(reg, new BulletItem(defP(), 7), "blaze_bullet");
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
