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
	private int enchatability;
	private int fireDelay;

	public GunItem(Properties properties, int fireDelay, int enchatability) {
		super(properties);
		this.enchatability = enchatability;
		this.fireDelay = fireDelay;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack gun = player.getHeldItem(hand);
		ItemStack ammo = player.findAmmo(gun);

		if (!ammo.isEmpty() || player.abilities.isCreativeMode) {
			if (ammo.isEmpty()) {
				ammo = new ItemStack(GunsWithoutRosesItems.flintBullet);
			}

			float speed = 1;
			boolean bulletFree = player.abilities.isCreativeMode;
			BulletItem bulletItem = (BulletItem) (ammo.getItem() instanceof BulletItem ? ammo.getItem() : GunsWithoutRosesItems.flintBullet);
			if (!world.isRemote) {
				AbstractArrowEntity abstractarrowentity = bulletItem.createProjectile(world, ammo, player);
				abstractarrowentity.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0.0F, speed * 3.0F, 1.0F);
				if (speed == 1.0F) {
					abstractarrowentity.setIsCritical(true);
				}
				abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;

				gun.damageItem(1, player, (p) -> p.sendBreakAnimation(player.getActiveHand()));
				world.addEntity(abstractarrowentity);

				if (!bulletFree) bulletItem.consume(ammo, player);
			}

			world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + speed * 0.5F);
			player.addStat(Stats.ITEM_USED.get(this));

			player.getCooldownTracker().setCooldown(this, fireDelay);
			return ActionResult.resultConsume(gun);
		}
		else return ActionResult.resultFail(gun);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public int getItemEnchantability() {
		return enchatability;
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
