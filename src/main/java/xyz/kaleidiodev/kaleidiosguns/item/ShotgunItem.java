package xyz.kaleidiodev.kaleidiosguns.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import xyz.kaleidiodev.kaleidiosguns.registry.ModEnchantments;

import javax.annotation.Nullable;
import java.util.List;

public class ShotgunItem extends GunItem {

	private final int bulletCount;

	public ShotgunItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability, int bulletCount) {
		super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
		this.bulletCount = bulletCount;
	}

	@Override
	protected void fireWeapon(World world, PlayerEntity player, ItemStack gun, ItemStack ammo, IBullet bulletItem, boolean bulletFree) {
		for (int i = 0; i < getBulletCount(gun); i++) super.fireWeapon(world, player, gun, ammo, bulletItem, bulletFree);
	}

	@Override
	protected void addExtraStatsTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip) {
		tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.shotgun.shots" + (isProjectileCountModified(stack) ? ".modified" : ""), getBulletCount(stack)));
	}

	protected int getBulletCount(ItemStack stack) {
		return bulletCount + EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.division, stack);
	}

	protected boolean isProjectileCountModified(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.division, stack) >= 1;
	}

}
