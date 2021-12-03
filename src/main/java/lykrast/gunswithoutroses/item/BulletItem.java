package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.entity.BulletEntity;
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

public class BulletItem extends Item implements IBullet {
	private int damage;

	public BulletItem(Properties properties, int damage) {
		super(properties);
		this.damage = damage;
	}

	@Override
	public BulletEntity createProjectile(World world, ItemStack stack, LivingEntity shooter) {
		BulletEntity entity = new BulletEntity(world, shooter);
		entity.setItem(stack);
		entity.setDamage(damage);
		return entity;
	}

	@Override
	public void consume(ItemStack stack, PlayerEntity player) {
		stack.shrink(1);
		if (stack.isEmpty()) {
			player.inventory.removeItem(stack);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.bullet.damage", damage).withStyle(TextFormatting.DARK_GREEN));
	}

}
