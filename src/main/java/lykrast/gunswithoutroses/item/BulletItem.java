package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class BulletItem extends Item implements IBullet {
	protected int damage;

	public BulletItem(Properties properties, int damage) {
		super(properties);
		this.damage = damage;
	}

	@Override
	public BulletEntity createProjectile(Level world, ItemStack stack, LivingEntity shooter) {
		BulletEntity entity = new BulletEntity(world, shooter);
		entity.setItem(stack);
		entity.setDamage(damage);
		return entity;
	}

	@Override
	public void consume(ItemStack stack, Player player) {
		stack.shrink(1);
		//this is in the bow but I don't think we actually need it?
		//pretty sure it's checked for empty in any thing to save
//		if (stack.isEmpty()) {
//			player.getInventory().removeItem(stack);
//		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.bullet.damage", damage).withStyle(ChatFormatting.DARK_GREEN));
	}

}
