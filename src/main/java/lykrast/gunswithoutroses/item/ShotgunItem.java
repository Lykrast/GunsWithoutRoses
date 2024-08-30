package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ShotgunItem extends GunItem {
	private int bulletCount;

	public ShotgunItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability, int bulletCount) {
		super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
		this.bulletCount = bulletCount;
	}

	@Override
	protected void shoot(Level world, Player player, ItemStack gun, ItemStack ammo, IBullet bulletItem, boolean bulletFree) {
		for (int i = 0; i < bulletCount; i++) super.shoot(world, player, gun, ammo, bulletItem, bulletFree || i > 0);
	}
	
	@Override
	public void shootAt(LivingEntity shooter, LivingEntity target, ItemStack gun, ItemStack ammo, IBullet bulletItem, double spreadMult, boolean bulletFree) {
		for (int i = 0; i < bulletCount; i++) super.shootAt(shooter, target, gun, ammo, bulletItem, spreadMult, bulletFree || i > 0);
	}
	
	@Override
	protected void addExtraStatsTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip) {
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.shotgun.shots", bulletCount).withStyle(ChatFormatting.GRAY));
	}

}
