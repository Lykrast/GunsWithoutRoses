package xyz.kaleidiodev.kaleidiosguns.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroupGuns extends ItemGroup {

	public static final ItemGroup INSTANCE = new ItemGroupGuns(ItemGroup.getGroupCountSafe(), "kaleidiosguns");

	public ItemGroupGuns(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ModItems.ironGun);
	}

}
