package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.registry.GWRItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GatlingItem extends GunItem {

	public GatlingItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability) {
		super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);

		if (!player.getAbilities().instabuild && player.getProjectile(itemstack).isEmpty()) {
			return InteractionResultHolder.fail(itemstack);
		}
		else {
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(itemstack);
		}
	}

	@Override
	public void onUseTick(Level world, LivingEntity user, ItemStack gun, int ticks) {
		if (user instanceof Player) {
			Player player = (Player) user;
			int used = getUseDuration(gun) - ticks;
			if (used > 0 && used % getFireDelay(gun, player) == 0) {
				//"Oh yeah I will use the vanilla method so that quivers can do their thing"
				//guess what the quivers suck
				ItemStack ammo = player.getProjectile(gun);

				if (!ammo.isEmpty() || player.getAbilities().instabuild) {
					if (ammo.isEmpty()) ammo = new ItemStack(GWRItems.flintBullet.get());

					IBullet bulletItem = (IBullet) (ammo.getItem() instanceof IBullet ? ammo.getItem() : GWRItems.flintBullet.get());
					if (!world.isClientSide) {
						boolean bulletFree = player.getAbilities().instabuild || !shouldConsumeAmmo(world, gun, player);

						//Workaround for quivers not respecting getAmmoPredicate()
						ItemStack shotAmmo = ammo.getItem() instanceof IBullet ? ammo : new ItemStack(GWRItems.flintBullet.get());
						shoot(world, player, gun, shotAmmo, bulletItem, bulletFree);

						gun.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
						if (!bulletFree) bulletItem.consume(ammo, player);
					}

					world.playSound(null, player.getX(), player.getY(), player.getZ(), getFireSound(), SoundSource.PLAYERS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
					player.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	protected void addExtraStatsTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip) {
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.gatling.hold").withStyle(ChatFormatting.GRAY));
	}

}
