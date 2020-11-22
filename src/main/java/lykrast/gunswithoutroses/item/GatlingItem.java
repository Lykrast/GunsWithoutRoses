package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.registry.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
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
			int used = getUseDuration(gun) - ticks;
			if (used > 0 && used % getFireDelay(gun, player) == 0) {
				//"Oh yeah I will use the vanilla method so that quivers can do their thing"
				//guess what the quivers suck
				ItemStack ammo = player.findAmmo(gun);

				if (!ammo.isEmpty() || player.abilities.isCreativeMode) {
					if (ammo.isEmpty()) ammo = new ItemStack(ModItems.flintBullet);

					IBullet bulletItem = (IBullet) (ammo.getItem() instanceof IBullet ? ammo.getItem() : ModItems.flintBullet);
					if (!world.isRemote) {
						boolean bulletFree = player.abilities.isCreativeMode || !shouldConsumeAmmo(gun, player);

						//Workaround for quivers not respecting getAmmoPredicate()
						ItemStack shotAmmo = ammo.getItem() instanceof IBullet ? ammo : new ItemStack(ModItems.flintBullet);
						shoot(world, player, gun, shotAmmo, bulletItem, bulletFree);

						gun.damageItem(1, player, (p) -> p.sendBreakAnimation(player.getActiveHand()));
						if (!bulletFree) bulletItem.consume(ammo, player);
					}

					world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), fireSound, SoundCategory.PLAYERS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
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
		tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gatling.hold").mergeStyle(TextFormatting.GRAY));
	}

}
