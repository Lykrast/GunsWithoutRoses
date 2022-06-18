package lykrast.gunswithoutroses.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroupGunsWithoutRoses extends CreativeModeTab {
	public static final CreativeModeTab INSTANCE = new ItemGroupGunsWithoutRoses(CreativeModeTab.getGroupCountSafe(), "gunswithoutroses");

	public ItemGroupGunsWithoutRoses(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ModItems.ironGun.get());
	}

}
