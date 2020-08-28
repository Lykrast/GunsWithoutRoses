package lykrast.gunswithoutroses;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroupGunsWithoutRoses extends ItemGroup {
	public static final ItemGroup INSTANCE = new ItemGroupGunsWithoutRoses(ItemGroup.getGroupCountSafe(), "gunswithoutroses");

	public ItemGroupGunsWithoutRoses(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(GunsWithoutRosesItems.ironGun);
	}

}
