package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class PrismarineBulletItem extends BulletItem {

	public PrismarineBulletItem(Properties properties, int damage) {
		super(properties, damage);
	}

	@Override
	public BulletEntity createProjectile(Level world, ItemStack stack, LivingEntity shooter) {
		BulletEntity entity = super.createProjectile(world, stack, shooter);
		entity.setWaterInertia(1);
		return entity;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.prismarine_bullet").withStyle(ChatFormatting.GRAY));
	}
}
