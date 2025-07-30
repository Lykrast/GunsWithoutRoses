package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BurstGunItem extends GunItem {
	//Unused in base GWR but want this for future addons and want to ensure consistency

	protected int burstSize, burstFireDelay;

	public BurstGunItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability, int burstSize) {
		super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
		this.burstSize = burstSize;
		burstFireDelay = 1;
	}
	
	public BurstGunItem burstFireDelay(int burstFireDelay) {
		this.burstFireDelay = Math.max(1, burstFireDelay);
		return this;
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
		if (user instanceof Player player) {
			//this goes from 0 to useduration-1
			//with a fire delay of n, we'd want to fire on 0, n, 2n... and so end on (burstSize-1)*burstFireDelay
			int used = getUseDuration(gun) - ticks;
			if (used % burstFireDelay == 0) findAmmoAndPlayerShoot(gun, player, world);
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
		//TODO find a way to display the burst fire delay
		int burstSize = getBurstSize(stack);
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.burst.size", Component.literal(Integer.toString(burstSize)).withStyle(ChatFormatting.WHITE))
				.withStyle(isBurstSizeModified(stack) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.DARK_GREEN));

		//fire rate
		//holding down right click with a useduration of N syncs up with N+?+firedelay
		//that is +0~2 and seems to be consistent with the same burstsize and firedelay pairs
		//+1 and +2 seem to most common so I'm going with +1, I doubt anyone is gonna metronome it
		int fireDelay = getFireDelay(stack, null);
		MutableComponent values = Component.translatable("tooltip.gunswithoutroses.gun.firerate.values", Component.literal(Integer.toString(fireDelay)).withStyle(ChatFormatting.WHITE),
				Component.literal(Integer.toString((60 * 20 * burstSize) / (fireDelay + getUseDuration(stack) + 1))).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GRAY);
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
		return (getBurstSize(stack)-1)*burstFireDelay+1;
	}

	@Override
	protected void addExtraStatsTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip) {
		//TODO tooltip
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.gatling.hold").withStyle(ChatFormatting.GRAY));
	}

}
