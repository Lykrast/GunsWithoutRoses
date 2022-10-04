package xyz.kaleidiodev.kaleidiosguns.item;

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
import xyz.kaleidiodev.kaleidiosguns.config.KGConfig;
import xyz.kaleidiodev.kaleidiosguns.entity.BulletEntity;

import javax.annotation.Nullable;
import java.util.List;

public class XPBulletItem extends BulletItem {

	public XPBulletItem(Properties properties, double damage) {
		super(properties, damage);
	}

	@Override
	public void consume(ItemStack stack, PlayerEntity player, ItemStack gunItem) {
		player.giveExperienceLevels(-costToUse(gunItem));
	}

	@Override
	public boolean hasAmmo(ItemStack stack, PlayerEntity player, ItemStack gunItem) {
		return player.experienceLevel >= costToUse(gunItem);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.xp_bullet").withStyle(TextFormatting.GRAY));
	}

	public static boolean isShot(ItemStack stack) {
		return !stack.isEmpty() && stack.getOrCreateTag().contains("shot");
	}
}
