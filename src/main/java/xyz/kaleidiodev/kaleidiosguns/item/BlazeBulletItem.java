package xyz.kaleidiodev.kaleidiosguns.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.kaleidiodev.kaleidiosguns.entity.BulletEntity;

import javax.annotation.Nullable;
import java.util.List;

public class BlazeBulletItem extends BulletItem {

	public BlazeBulletItem(Properties properties, int damage) {
		super(properties, damage);
	}

	@Override
	public BulletEntity createProjectile(World world, ItemStack stack, LivingEntity shooter) {
		BulletEntity entity = super.createProjectile(world, stack, shooter);
		entity.setSecondsOnFire(100);
		return entity;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.blaze_bullet").withStyle(TextFormatting.GRAY));
	}
}
