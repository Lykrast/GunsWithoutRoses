package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.registry.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class GatlingItem extends GunItem {

	public GatlingItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability) {
		super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getHeldItem(hand);

		if (!player.abilities.isCreativeMode && player.findAmmo(itemstack).isEmpty()) {
			return ActionResult.resultFail(itemstack);
		}
		else {
			player.setActiveHand(hand);
			return ActionResult.resultConsume(itemstack);
		}
	}

	@Override
	public void onUse(World world, LivingEntity user, ItemStack gun, int ticks) {
		if (user instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) user;
			if (ticks < 72000 && ticks % getFireDelay(gun, player) == 0) {
				ItemStack ammo = player.findAmmo(gun);

				if (!ammo.isEmpty() || player.abilities.isCreativeMode) {
					if (ammo.isEmpty() || ammo.getItem() == Items.ARROW) {
						ammo = new ItemStack(ModItems.flintBullet);
					}

					BulletItem bulletItem = (BulletItem) (ammo.getItem() instanceof BulletItem ? ammo.getItem() : ModItems.flintBullet);
					if (!world.isRemote) {
						boolean bulletFree = player.abilities.isCreativeMode || !shouldConsumeAmmo(gun, player);

						shoot(world, player, gun, ammo, bulletItem, bulletFree);

						gun.damageItem(1, player, (p) -> p.sendBreakAnimation(player.getActiveHand()));
						if (!bulletFree) bulletItem.consume(ammo, player);
					}

					world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
					player.addStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	protected void addExtraStatsTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip) {
		tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gatling.hold").func_240699_a_(TextFormatting.GRAY));
	}

}
