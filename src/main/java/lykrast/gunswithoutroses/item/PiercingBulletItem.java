package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.entity.BulletEntity;
import lykrast.gunswithoutroses.entity.PiercingBulletEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PiercingBulletItem extends BulletItem {
	protected int pierce;
	protected double pierceMult;

	public PiercingBulletItem(Properties properties, int damage, int pierce) {
		this(properties, damage, pierce, 1);
	}

	public PiercingBulletItem(Properties properties, int damage, int pierce, double pierceMult) {
		super(properties, damage);
		this.pierce = pierce;
		this.pierceMult = pierceMult;
	}

	@Override
	public BulletEntity createProjectile(Level world, ItemStack stack, LivingEntity shooter) {
		PiercingBulletEntity entity = new PiercingBulletEntity(world, shooter);
		entity.setItem(stack);
		entity.setDamage(damage);
		entity.setPierce(pierce);
		entity.setPierceMultiplier(pierceMult);
		return entity;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.bullet.piercing", pierce).withStyle(ChatFormatting.GRAY));
		if (pierceMult > 1) tooltip.add(Component.translatable("tooltip.gunswithoutroses.bullet.piercing.mult.gain", (int)Math.round(100*(pierceMult-1))).withStyle(ChatFormatting.GRAY));
		else if (pierceMult < 1) tooltip.add(Component.translatable("tooltip.gunswithoutroses.bullet.piercing.mult.lose", (int)Math.round(100*(1-pierceMult))).withStyle(ChatFormatting.GRAY));
	}
}
