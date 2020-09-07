package lykrast.gunswithoutroses.item;

import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IBullet {

	BulletEntity createProjectile(World world, ItemStack stack, LivingEntity shooter);

	/**
	 * Uses up 1 item worth of ammo. Can be used for RF or magic based bullet
	 * pouches or something.
	 */
	void consume(ItemStack stack, PlayerEntity player);

}