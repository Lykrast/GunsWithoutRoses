package lykrast.gunswithoutroses.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BulletBagContainer extends SimpleContainer {
	//basically just copied botania's itembackedcontainer but squashed down the helpers, and put the things for the bag
	//Botania License: http://botaniamod.net/license.php
	//https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/common/item/ItemBackedInventory.java
	//https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/common/helper/ItemNBTHelper.java
	private static final String TAG = "Items";
	private final ItemStack stack;

	public BulletBagContainer(ItemStack stack, int expectedSize) {
		super(expectedSize);
		this.stack = stack;

		ListTag lst = (!stack.isEmpty() && stack.hasTag() && stack.getOrCreateTag().contains(TAG)) ? stack.getOrCreateTag().getList(TAG, Tag.TAG_COMPOUND) : new ListTag();
		for (int i = 0; i < expectedSize && i < lst.size(); i++) {
			setItem(i, ItemStack.of(lst.getCompound(i)));
		}
	}
	
	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return BulletBagItem.canBag(stack);
	}

	@Override
	public boolean stillValid(Player player) {
		return !stack.isEmpty();
	}

	@Override
	public void setChanged() {
		super.setChanged();
		ListTag list = new ListTag();
		for (int i = 0; i < getContainerSize(); i++) {
			list.add(getItem(i).save(new CompoundTag()));
		}
		stack.getOrCreateTag().put(TAG, list);
	}
}
