package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class ShotgunItem extends GunItem {

	private int bulletCount;

	public ShotgunItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability, int bulletCount) {
		super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
		this.bulletCount = bulletCount;
	}

	@Override
	protected void shoot(World world, PlayerEntity player, ItemStack gun, ItemStack ammo, IBullet bulletItem, boolean bulletFree) {
		for (int i = 0; i < bulletCount; i++) super.shoot(world, player, gun, ammo, bulletItem, bulletFree);
	}

	@Override
	protected void addExtraStatsTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip) {
		tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.shotgun.shots", bulletCount).withStyle(TextFormatting.GRAY));
	}

}
