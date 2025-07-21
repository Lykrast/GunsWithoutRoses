package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.entity.BouncingBulletEntity;
import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BouncingBulletItem extends BulletItem {
	protected int bounces;
	protected double bounceMult;

	public BouncingBulletItem(Properties properties, int damage, int bounces) {
		this(properties, damage, bounces, 1);
	}

	public BouncingBulletItem(Properties properties, int damage, int bounces, double bounceMult) {
		super(properties, damage);
		this.bounces = bounces;
		this.bounceMult = bounceMult;
	}

	@Override
	public BulletEntity createProjectile(Level world, ItemStack stack, LivingEntity shooter) {
		BouncingBulletEntity entity = new BouncingBulletEntity(world, shooter);
		entity.setItem(stack);
		entity.setDamage(damage);
		entity.setBounces(bounces);
		entity.setBounceMultiplier(bounceMult);
		return entity;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.bullet.bouncing", bounces).withStyle(ChatFormatting.GRAY));
		if (bounceMult > 1) tooltip.add(Component.translatable("tooltip.gunswithoutroses.bullet.bouncing.mult.gain", (int)Math.round(100*(bounceMult-1))).withStyle(ChatFormatting.GRAY));
		else if (bounceMult < 1) tooltip.add(Component.translatable("tooltip.gunswithoutroses.bullet.bouncing.mult.lose", (int)Math.round(100*(1-bounceMult))).withStyle(ChatFormatting.GRAY));
	}
}
