package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import lykrast.gunswithoutroses.gui.BulletBagMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GWRMenu {
	public static final MenuType<BulletBagMenu> BAG_CONTAINER = IForgeMenuType.create(BulletBagMenu::fromNetwork);
	public static final DeferredRegister<MenuType<?>> REG = DeferredRegister.create(ForgeRegistries.MENU_TYPES, GunsWithoutRoses.MODID);
	
	static {
		REG.register("bullet_bag", () -> BAG_CONTAINER);
	}
}
