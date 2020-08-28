package lykrast.gunswithoutroses.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BulletItem extends Item {
	public BulletItem(Properties properties) {
		super(properties);
	}

	// TODO
	public AbstractArrowEntity createProjectile(World worldIn, ItemStack stack, LivingEntity shooter) {
		ArrowEntity arrowentity = new ArrowEntity(worldIn, shooter);
		return arrowentity;
	}

	/**
	 * Uses up 1 item worth of ammo.
	 * Can be used for RF or magic based bullet pouches or something.
	 */
	public void consume(ItemStack stack, PlayerEntity player) {
		stack.shrink(1);
		if (stack.isEmpty()) {
			player.inventory.deleteStack(stack);
		}
	}

}
