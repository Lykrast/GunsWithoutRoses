package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HungerBulletItem extends BulletItem {

	public HungerBulletItem(Properties properties, int damage) {
		super(properties, damage);
	}
	
	@Override
	public void consume(ItemStack stack, PlayerEntity player) {
		if (player.getFoodData().getFoodLevel() <= 0) player.hurt(DamageSource.STARVE, 1);
		player.causeFoodExhaustion(3);
	}
	
	@Override
	public BulletEntity createProjectile(World world, ItemStack stack, LivingEntity shooter) {
		ItemStack fake = new ItemStack(this);
		fake.getOrCreateTag().putBoolean("shot", true);
		return super.createProjectile(world, fake, shooter);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.hunger_bullet").withStyle(TextFormatting.GRAY));
	}
	
	public static boolean isShot(ItemStack stack) {
		return !stack.isEmpty() && stack.getOrCreateTag().contains("shot") && stack.getOrCreateTag().getBoolean("shot");
	}
}
