package lykrast.gunswithoutroses.items;

import java.util.function.Predicate;

import lykrast.gunswithoutroses.GunsWithoutRosesItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class GunItem extends ShootableItem {

	public GunItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack gun = playerIn.getHeldItem(handIn);
		ItemStack ammo = playerIn.findAmmo(gun);

		if (!ammo.isEmpty() || playerIn.abilities.isCreativeMode) {
			if (ammo.isEmpty()) {
				ammo = new ItemStack(GunsWithoutRosesItems.flintBullet);
			}

			float speed = 1;
			boolean bulletFree = playerIn.abilities.isCreativeMode;
			BulletItem bulletItem = (BulletItem) (ammo.getItem() instanceof BulletItem ? ammo.getItem() : GunsWithoutRosesItems.flintBullet);
			if (!worldIn.isRemote) {
				AbstractArrowEntity abstractarrowentity = bulletItem.createProjectile(worldIn, ammo, playerIn);
				abstractarrowentity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, speed * 3.0F, 1.0F);
				if (speed == 1.0F) {
					abstractarrowentity.setIsCritical(true);
				}
				abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;

				gun.damageItem(1, playerIn, (p_220009_1_) -> p_220009_1_.sendBreakAnimation(playerIn.getActiveHand()));
				worldIn.addEntity(abstractarrowentity);

				if (!bulletFree) {
					ammo.shrink(1);
					if (ammo.isEmpty()) {
						playerIn.inventory.deleteStack(ammo);
					}
				}
			}

			worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + speed * 0.5F);
			playerIn.addStat(Stats.ITEM_USED.get(this));
			
	         return ActionResult.resultConsume(gun);
		}
		else return ActionResult.resultFail(gun);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	private static final Predicate<ItemStack> BULLETS = (stack) -> stack.getItem() instanceof BulletItem;

	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return BULLETS;
	}

	@Override
	public int func_230305_d_() {
		// No idea what this is yet, so using the Bow value (Crossbow is 8)
		return 15;
	}

}
