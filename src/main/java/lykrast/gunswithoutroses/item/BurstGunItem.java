package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.registry.GWRItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BurstGunItem extends GunItem {
	//Unused in base GWR but want this for future addons and want to ensure consistency

	protected int burstSize;

	public BurstGunItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability, int burstSize) {
		super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
		this.burstSize = burstSize;
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
		//I'm copying that part a lot, maybe I need to extract it
		if (user instanceof Player) {
			Player player = (Player) user;
			ItemStack ammo = player.getProjectile(gun);
			if (!ammo.isEmpty() || player.getAbilities().instabuild) {
				if (!world.isClientSide) {
					//this is for creative
					if (ammo.isEmpty()) ammo = new ItemStack(GWRItems.ironBullet.get());
					//There was at least one instance of quiver mod not respecting getAmmoPredicate()
					//so I have to put wayyy more instanceof IBullet checks than I should need to >:(
					IBullet parentBullet = (IBullet) (ammo.getItem() instanceof IBullet ? ammo.getItem() : GWRItems.ironBullet.get());
					//For the bullet bag we doing the indirection here
					ItemStack firedAmmo = ammo;
					IBullet firedBullet = parentBullet;
					if (parentBullet.hasDelegate(ammo, player)) {
						firedAmmo = parentBullet.getDelegate(ammo, player);
						firedBullet = (IBullet) (firedAmmo.getItem() instanceof IBullet ? firedAmmo.getItem() : GWRItems.ironBullet.get());
					}

					boolean bulletFree = player.getAbilities().instabuild || !shouldConsumeAmmo(gun, player);

					//Workaround for quivers not respecting getAmmoPredicate()
					if (!(firedAmmo.getItem() instanceof IBullet)) firedAmmo = new ItemStack(GWRItems.ironBullet.get());
					shoot(world, player, gun, firedAmmo, firedBullet, bulletFree);

					gun.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
					if (!bulletFree) parentBullet.consume(ammo, player);
				}

				world.playSound(null, player.getX(), player.getY(), player.getZ(), getFireSound(), SoundSource.PLAYERS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
				player.awardStat(Stats.ITEM_USED.get(this));
			}
		}
	}

	@Override
	public void releaseUsing(ItemStack gun, Level world, LivingEntity user, int ticks) {
		//super is empty
		if (user instanceof Player player) player.getCooldowns().addCooldown(this, getFireDelay(gun, player));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack gun, Level world, LivingEntity user) {
		if (user instanceof Player player) player.getCooldowns().addCooldown(this, getFireDelay(gun, player));
		return super.finishUsingItem(gun, world, user);
	}

	@Override
	protected void customFireRateTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip) {
		//burst size
		int burstSize = getBurstSize(stack);
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.burst.size", Component.literal(Integer.toString(burstSize)).withStyle(ChatFormatting.WHITE))
				.withStyle(isBurstSizeModified(stack) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.DARK_GREEN));

		//fire rate
		//holding down right click with a burstsize of N syncs up with N+?+firedelay
		//that is +0~2 and seems to be consistent with the same burstsize and firedelay pairs
		//+1 and +2 seem to most common so I'm going with +1, I doubt anyone is gonna metronome it
		int fireDelay = getFireDelay(stack, null);
		MutableComponent values = Component.translatable("tooltip.gunswithoutroses.gun.firerate.values", Component.literal(Integer.toString(fireDelay)).withStyle(ChatFormatting.WHITE),
				Component.literal(Integer.toString((60 * 20 * burstSize) / (fireDelay + burstSize + 1))).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY);
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.burst.firerate", values).withStyle(isFireDelayModified(stack) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.DARK_GREEN));
	}
	
	protected boolean isBurstSizeModified(ItemStack stack) {
		//for now not putting an enchant here, just like I did not for shotgun projectiles
		return false;
	}
	
	public int getBurstSize(ItemStack stack) {
		//not player sensitive because getUseDuration isn't
		return burstSize;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return getBurstSize(stack);
	}

	@Override
	protected void addExtraStatsTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip) {
		//TODO tooltip
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.gatling.hold").withStyle(ChatFormatting.GRAY));
	}

}
