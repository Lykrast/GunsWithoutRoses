package lykrast.gunswithoutroses.item;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IBullet {

	BulletEntity createProjectile(World world, ItemStack stack, LivingEntity shooter);

	/**
	 * Uses up 1 item worth of ammo. Can be used for RF or magic based bullet pouches or something.
	 */
	void consume(ItemStack stack, PlayerEntity player);
	
	/**
	 * Whether that stack can be fired as ammo. Can be used for RF bullet pouches or something so that they get skipped if they're out of juice.
	 */
	default boolean hasAmmo(ItemStack stack) {
		return !stack.isEmpty();
	}
	
	//might change later, it's mostly for testing now
	default void onLivingEntityHit(Entity projectile, LivingEntity target, @Nullable Entity shooter, World world) {}

}