package xyz.kaleidiodev.kaleidiosguns.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

public class BulletItem extends Item implements IBullet {

	public final double damage;

	public BulletItem(Properties properties, double damage) {
		super(properties);
		this.damage = damage;
	}

	@Override
	public BulletEntity createProjectile(World world, ItemStack stack, LivingEntity shooter, boolean isPlasma) {
		ItemStack fake = new ItemStack(this);
		fake.getOrCreateTag().putBoolean("isPlasma", isPlasma);
		BulletEntity entity = new BulletEntity(world, shooter);
		entity.setItem(fake);
		entity.setDamage(damage);
		return entity;
	}

	@Override
	public void consume(ItemStack stack, PlayerEntity player) {
		int cost = 1;

		if (player.getItemInHand(player.getUsedItemHand()).getItem() instanceof GatlingItem) cost = KGConfig.gatlingCost.get();
		if (player.getItemInHand(player.getUsedItemHand()).getItem() instanceof ShotgunItem) cost = KGConfig.shotgunCost.get();
		if (player.getItemInHand(player.getUsedItemHand()).getItem() instanceof GunItem) {
			if (((GunItem)player.getItemInHand(player.getUsedItemHand()).getItem()).isExplosive) cost = KGConfig.launcherCost.get();
			if (!(((GunItem)player.getItemInHand(player.getUsedItemHand()).getItem()).isExplosive) &&
					(((GunItem)player.getItemInHand(player.getUsedItemHand()).getItem()).hasPerfectAccuracy()) &&
					!(player.getItemInHand(player.getUsedItemHand()).getItem() instanceof GatlingItem)) cost = KGConfig.sniperCost.get();
			if (!(((GunItem)player.getItemInHand(player.getUsedItemHand()).getItem()).isExplosive) &&
					!(((GunItem)player.getItemInHand(player.getUsedItemHand()).getItem()).hasPerfectAccuracy()) &&
					!(player.getItemInHand(player.getUsedItemHand()).getItem() instanceof GatlingItem) &&
					!(player.getItemInHand(player.getUsedItemHand()).getItem() instanceof ShotgunItem)) cost = KGConfig.pistolCost.get();
		}

		stack.shrink(cost);
		if (stack.isEmpty()) {
			player.inventory.removeItem(stack);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.bullet.damage", damage).withStyle(TextFormatting.DARK_GREEN));
	}

	@Override
	public boolean isFoil(ItemStack pStack) {
		return pStack.getOrCreateTag().getBoolean("isPlasma");
	}
}
