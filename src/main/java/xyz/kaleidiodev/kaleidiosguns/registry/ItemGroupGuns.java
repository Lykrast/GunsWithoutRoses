package xyz.kaleidiodev.kaleidiosguns.registry;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ItemGroupGuns extends ItemGroup {

	public static final ItemGroup INSTANCE = new ItemGroupGuns(ItemGroup.getGroupCountSafe(), "kaleidiosguns").setEnchantmentCategories(ModEnchantments.TYPE_GUN);

	public ItemGroupGuns(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ModItems.ironGun);
	}

}
