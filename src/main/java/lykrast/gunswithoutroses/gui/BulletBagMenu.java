package lykrast.gunswithoutroses.gui;

import lykrast.gunswithoutroses.item.BulletBagItem;
import lykrast.gunswithoutroses.registry.GWRMenu;
import lykrast.gunswithoutroses.registry.GWRSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BulletBagMenu extends AbstractContainerMenu {
	//still copying botania over there https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/client/gui/bag/FlowerPouchContainer.java
	//https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/client/gui/box/BaubleBoxContainer.java
	public static BulletBagMenu fromNetwork(int windowId, Inventory inv, FriendlyByteBuf buf) {
		InteractionHand hand = buf.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
		return new BulletBagMenu(windowId, inv, inv.player.getItemInHand(hand));
	}

	private final ItemStack bag;
	public final Container bagInv;

	@SuppressWarnings("resource")
	public BulletBagMenu(int id, Inventory playerInv, ItemStack bag) {
		super(GWRMenu.BAG_CONTAINER, id);
		this.bag = bag;
		
		//Chests also do their opening check in the constructor, which is the one that will play sound
		playerInv.player.playSound(GWRSounds.bagOpen.get(), 0.8F, 0.9F + playerInv.player.level().getRandom().nextFloat() * 0.1F);

		if (!playerInv.player.level().isClientSide) bagInv = BulletBagItem.getInventory(bag);
		else bagInv = new SimpleContainer(BulletBagItem.SIZE);

		//bag
		for (int i = 0; i < BulletBagItem.SIZE; ++i) {
			addSlot(new BulletSlot(bagInv, i, 8 + i * 18, 20));
		}

		//inventory
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 51 + row * 18));
			}
		}

		//hotbar
		for (int i = 0; i < 9; ++i) {
			if (playerInv.getItem(i) == bag) {
				addSlot(new LockedSlot(playerInv, i, 8 + i * 18, 109));
			}
			else {
				addSlot(new Slot(playerInv, i, 8 + i * 18, 109));
			}
		}
	}

	@Override
	public boolean stillValid(Player player) {
		ItemStack main = player.getMainHandItem();
		ItemStack off = player.getOffhandItem();
		return !main.isEmpty() && main == bag || !off.isEmpty() && off == bag;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = slots.get(slotIndex);

		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();

			int bagStart = 0;
			int bagEnd = bagStart + BulletBagItem.SIZE;
			int invEnd = bagEnd + 36;

			if (slotIndex < bagEnd) {
				if (!moveItemStackTo(itemstack1, bagEnd, invEnd, true)) return ItemStack.EMPTY;
			} else {
				if (!itemstack1.isEmpty() && BulletBagItem.canBag(itemstack1) && !moveItemStackTo(itemstack1, bagStart, bagEnd, false)) return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) slot.set(ItemStack.EMPTY);
			else slot.setChanged();

			if (itemstack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}
	
	@SuppressWarnings("resource")
	@Override
	public void removed(Player player) {
		if (!player.level().isClientSide) bag.getOrCreateTag().putBoolean(BulletBagItem.OPEN, false);
		player.playSound(GWRSounds.bagClose.get(), 0.8F, 0.9F + player.level().getRandom().nextFloat() * 0.1F);
		super.removed(player);
	}

	private static class BulletSlot extends Slot {
		public BulletSlot(Container inv, int index, int x, int y) {
			super(inv, index, x, y);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return BulletBagItem.canBag(stack);
		}

	}

	private static class LockedSlot extends Slot {
		public LockedSlot(Container inv, int index, int x, int y) {
			super(inv, index, x, y);
		}

		@Override
		public boolean mayPickup(Player player) {
			return false;
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return false;
		}
	}
}
