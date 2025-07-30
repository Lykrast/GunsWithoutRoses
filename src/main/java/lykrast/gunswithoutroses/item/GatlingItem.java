package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.registry.GWRAttributes;
import lykrast.gunswithoutroses.registry.GWREnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GatlingItem extends GunItem {
	protected double fireDelayFractional;

	//Uuuuh whoopsies while it's fine in code to change that int to double, it's not fine in execution
	//and if I deprecate this constructor then writing a plain 4 gives the warning
	//I'll leave it here to not break existing addons
	public GatlingItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability) {
		this(properties, bonusDamage, damageMultiplier, (double)fireDelay, inaccuracy, enchantability);
	}

	public GatlingItem(Properties properties, int bonusDamage, double damageMultiplier, double fireDelay, double inaccuracy, int enchantability) {
		super(properties, bonusDamage, damageMultiplier, (int) Math.ceil(fireDelay), inaccuracy, enchantability);
		fireDelayFractional = fireDelay;
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
		if (user instanceof Player player) {
			int used = getUseDuration(gun) - ticks;
			if (used > 0) {
				double delay = getFireDelayFractional(gun, player);
				//with 1.5 tick delay we should fire at 1.5, 3, 4.5, 6... but we can't do that
				//so we check how much we're supposed to have fired since previous tick to know how much to fire
				//so at tick 1 we should have fired 0, at 2 we passed 1.5 so should have fired 1, and at 3 it should be 2 fired and so on
				int toFire = (int) (Math.floor(used/delay) - Math.floor((used-1)/delay));
				//this should be a for i from 0 to toFire, to let fire delays under 1 tick to work
				//but I don't want that, 1 per tick is already very strong and firing more quickly just means
				//it's kinda like a shotgun, so wrong feeling
				if (toFire > 0) findAmmoAndPlayerShoot(gun, player, world);
			}
		}
	}
	
	@Override
	public int getFireDelay(ItemStack stack, @Nullable LivingEntity shooter) {
		return Math.max(1, (int)Math.ceil(getFireDelayFractional(stack, shooter)));
	}
	
	/**
	 * getFireDelay but can take a double, used by the gatling's firing logic.
	 */
	public double getFireDelayFractional(ItemStack stack, @Nullable LivingEntity shooter) {
		int sleight = stack.getEnchantmentLevel(GWREnchantments.sleightOfHand.get());
		double delay = sleight > 0 ? GWREnchantments.sleightModifyFractional(sleight, fireDelayFractional) : fireDelayFractional;
		if (shooter != null && shooter.getAttribute(GWRAttributes.fireDelay.get()) != null) delay = (delay * shooter.getAttributeValue(GWRAttributes.fireDelay.get()));
		return Math.max(1, delay);
	}
	
	@Override
	protected void customFireRateTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip) {
		double fireDelay = getFireDelayFractional(stack, null);
		MutableComponent values = Component.translatable("tooltip.gunswithoutroses.gun.firerate.values", 
				Component.literal(ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(fireDelay)).withStyle(ChatFormatting.WHITE),
				Component.literal(Integer.toString((int)((60 * 20) / fireDelay))).withStyle(ChatFormatting.WHITE))
				.withStyle(ChatFormatting.GRAY);
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.firerate", values).withStyle(isFireDelayModified(stack) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.DARK_GREEN));
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
