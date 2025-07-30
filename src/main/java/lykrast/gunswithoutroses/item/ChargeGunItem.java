package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.registry.GWREnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class ChargeGunItem extends GunItem {
	//Unused in base GWR but want this for future addons and want to ensure consistency

	protected int chargeTime;

	public ChargeGunItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability, int chargeTime) {
		super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
		this.chargeTime = chargeTime;
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
		//TODO sound?
	}

	@Override
	public ItemStack finishUsingItem(ItemStack gun, Level world, LivingEntity user) {
		//I'm copying that part a lot, maybe I need to extract it
		if (user instanceof Player player) {
			if (findAmmoAndPlayerShoot(gun, player, world)) player.getCooldowns().addCooldown(this, getFireDelay(gun, player));
		}
		return super.finishUsingItem(gun, world, user);
	}

	@Override
	protected void customFireRateTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip) {
		int chargeTime = getChargeTime(stack);
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.charge.time", Component.literal(Integer.toString(chargeTime)).withStyle(ChatFormatting.WHITE))
				.withStyle(isChargeTimeModified(stack) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.DARK_GREEN));
		
		int fireDelay = getFireDelay(stack, null);
		MutableComponent values = Component.translatable("tooltip.gunswithoutroses.gun.firerate.values", 
				Component.literal(Integer.toString(fireDelay)).withStyle(ChatFormatting.WHITE),
				Component.literal(Integer.toString((60 * 20) / (fireDelay + chargeTime))).withStyle(ChatFormatting.WHITE))
				.withStyle(ChatFormatting.GRAY);
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.firerate", values).withStyle(isFireDelayModified(stack) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.DARK_GREEN));
	}
	
	protected boolean isChargeTimeModified(ItemStack stack) {
		return stack.getEnchantmentLevel(Enchantments.QUICK_CHARGE) >= 1;
	}
	
	public int getChargeTime(ItemStack stack) {
		//not player sensitive because getUseDuration isn't
		int quickcharge = stack.getEnchantmentLevel(Enchantments.QUICK_CHARGE);
		int time = quickcharge > 0 ? GWREnchantments.quickChargeModify(quickcharge, chargeTime) : chargeTime;
		return Math.max(1, time);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		//allow vanilla quick charge
		if (enchantment == Enchantments.QUICK_CHARGE) return true;
		return super.canApplyAtEnchantingTable(stack, enchantment);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return getChargeTime(stack);
	}

	@Override
	protected void addExtraStatsTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip) {
		//TODO tooltip
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.charge.hold").withStyle(ChatFormatting.GRAY));
	}

}
