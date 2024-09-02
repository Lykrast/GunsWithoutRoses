package lykrast.gunswithoutroses.item;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import lykrast.gunswithoutroses.entity.BulletEntity;
import lykrast.gunswithoutroses.gui.BulletBagMenu;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraftforge.network.NetworkHooks;

public class BulletBagItem extends Item implements IBullet {
	//Copied from botania's flower pouch/bauble box
	public static final String OPEN = "open";
	public static final int SIZE = 9; //changing that will require gui changes, but it should make some numbers clearer
	public static final TagKey<Item> IS_BULLET = ItemTags.create(GunsWithoutRoses.rl("bullet"));

	public BulletBagItem(Properties properties) {
		super(properties);
	}

	public static SimpleContainer getInventory(ItemStack stack) {
		return new BulletBagContainer(stack, SIZE);
	}

	//The bullet pouch is an IBullet but does not have the bullet tag
	//I don't think anyone would tag the bag as a bullet, hope not, won't be me
	public static boolean canBag(ItemStack stack) {
		return stack.is(IS_BULLET);
	}

	//Bullet stuff
	@Override
	public boolean hasAmmo(ItemStack stack) {
		SimpleContainer content = getInventory(stack);
		for (int i = 0; i < content.getContainerSize(); i++) {
			ItemStack bul = content.getItem(i);
			//the predicate fails on empty itemstacks (because their item is considered air, which is not a IBullet)
			//so no need to check for isEmpty()
			if (GunItem.BULLETS.test(bul)) return true;
		}
		return false;
	}

	@Override
	public BulletEntity createProjectile(Level world, ItemStack stack, LivingEntity shooter) {
		//We're not supposed to do more to the pouch than check if it has ammo, it should give its content for the actual shooting
		//though from what I checked, at worst it will just not fire and error the console
		return null;
	}

	@Override
	public boolean hasDelegate(ItemStack stack, Player player) {
		return true;
	}

	@Override
	public ItemStack getDelegate(ItemStack stack, Player player) {
		SimpleContainer content = getInventory(stack);
		for (int i = 0; i < content.getContainerSize(); i++) {
			ItemStack bul = content.getItem(i);
			if (GunItem.BULLETS.test(bul)) return bul;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void consume(ItemStack stack, Player player) {
		//Get back the first bullet we would have chosen (which shouldn't have changed) to consume it
		SimpleContainer content = getInventory(stack);
		for (int i = 0; i < content.getContainerSize(); i++) {
			ItemStack bul = content.getItem(i);
			if (GunItem.BULLETS.test(bul)) {
				((IBullet)bul.getItem()).consume(bul, player);
				content.setChanged();
				break;
			}
		}
	}

	//Botania stuff for the inventory

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		if (!world.isClientSide) {
			ItemStack stack = player.getItemInHand(hand);
			stack.getOrCreateTag().putBoolean(BulletBagItem.OPEN, true);
			NetworkHooks.openScreen((ServerPlayer) player, new MenuProvider() {
				@Override
				public Component getDisplayName() {
					return stack.getHoverName();
				}

				@Override
				public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
					return new BulletBagMenu(syncId, inv, stack);
				}
			}, buf -> buf.writeBoolean(hand == InteractionHand.MAIN_HAND));
		}
		return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide());
	}

	@Override
	public void onDestroyed(ItemEntity entity, DamageSource source) {
		SimpleContainer container = getInventory(entity.getItem());
		Stream<ItemStack> stream = IntStream.range(0, container.getContainerSize()).mapToObj(container::getItem).filter(s -> !s.isEmpty());
		ItemUtils.onContainerDestroyed(entity, stream);
		container.clearContent();
	}

	//still botania here
	//I love you botania team <3
	//https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/common/helper/InventoryHelper.java

	@Override
	public boolean overrideStackedOnOther(ItemStack bag, Slot slot, ClickAction clickAction, Player player) {
		if (!(player.containerMenu instanceof BulletBagMenu) && clickAction == ClickAction.SECONDARY) {
			ItemStack toInsert = slot.getItem();
			if (toInsert.isEmpty()) return false;
			SimpleContainer inventory = getInventory(bag);
			if (simulateTransfer(inventory, toInsert, null).isEmpty()) {
				ItemStack taken = slot.safeTake(toInsert.getCount(), Integer.MAX_VALUE, player);
				HopperBlockEntity.addItem(null, inventory, taken, null);
				playInsertSound(player);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack bag, ItemStack toInsert, Slot slot, ClickAction clickAction, Player player, SlotAccess cursorAccess) {
		if (!(player.containerMenu instanceof BulletBagMenu) && clickAction == ClickAction.SECONDARY && !toInsert.isEmpty()) {
			SimpleContainer inventory = getInventory(bag);
			if (simulateTransfer(inventory, toInsert, null).isEmpty()) {
				HopperBlockEntity.addItem(null, inventory, toInsert, null);
				cursorAccess.set(ItemStack.EMPTY);
				playInsertSound(player);
				return true;
			}
		}
		return false;
	}
	
	private void playInsertSound(Player player) {
		player.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
	}

	// [VanillaCopy] HopperBlockEntity#transfer but simulates instead of doing it
	private static ItemStack simulateTransfer(Container to, ItemStack stack, Direction side) {
		stack = stack.copy();

		if (to instanceof WorldlyContainer sidedInventory && side != null) {
			int[] is = sidedInventory.getSlotsForFace(side);

			for (int i = 0; i < is.length && !stack.isEmpty(); ++i) {
				stack = simulateTransfer(to, stack, is[i], side);
			}
		}
		else {
			int j = to.getContainerSize();

			for (int k = 0; k < j && !stack.isEmpty(); ++k) {
				stack = simulateTransfer(to, stack, k, side);
			}
		}

		return stack;
	}

	// [VanillaCopy] HopperBlockEntity without modifying the destination inventory. `stack` is still modified
	private static ItemStack simulateTransfer(Container to, ItemStack stack, int slot, Direction direction) {
		ItemStack itemStack = to.getItem(slot);
		if (canPlaceItemInContainer(to, stack, slot, direction)) {
			if (itemStack.isEmpty()) {
				// to.setStack(slot, stack);
				stack = ItemStack.EMPTY;
			}
			else if (canMergeItems(itemStack, stack)) {
				int i = stack.getMaxStackSize() - itemStack.getCount();
				int j = Math.min(stack.getCount(), i);
				stack.shrink(j);
				// itemStack.increment(j);
			}
		}

		return stack;
	}

	//ok so botania access transformers those hopper methods, so we copying them again woooh

	//HopperBlockEntity#canPlaceItemInContainer
	private static boolean canPlaceItemInContainer(Container p_59335_, ItemStack p_59336_, int p_59337_, @Nullable Direction p_59338_) {
		if (!p_59335_.canPlaceItem(p_59337_, p_59336_)) {
			return false;
		}
		else {
			if (p_59335_ instanceof WorldlyContainer) {
				WorldlyContainer worldlycontainer = (WorldlyContainer) p_59335_;
				if (!worldlycontainer.canPlaceItemThroughFace(p_59337_, p_59336_, p_59338_)) { return false; }
			}

			return true;
		}
	}

	//HopperBlockEntity#canMergeItems
	private static boolean canMergeItems(ItemStack p_59345_, ItemStack p_59346_) {
		return p_59345_.getCount() <= p_59345_.getMaxStackSize() && ItemStack.isSameItemSameTags(p_59345_, p_59346_);
	}
}
