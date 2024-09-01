package lykrast.gunswithoutroses.item;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IBullet {
	/**
	 * Creates a projectile and set its stats and stuff. The gun will give it velocity and spawn it in the world.
	 */
	BulletEntity createProjectile(Level world, ItemStack stack, LivingEntity shooter);

	/**
	 * Uses up 1 item worth of ammo. Can be used for RF or magic based bullet pouches or something.
	 * <br/>If a delegate is involved, you have to fetch it back yourself (but the itemstack is not changed beyond the getDelegate call)
	 */
	void consume(ItemStack stack, Player player);
	
	/**
	 * Whether that stack can be fired as ammo. Can be used for RF bullet pouches or something so that they get skipped if they're out of juice.
	 */
	default boolean hasAmmo(ItemStack stack) {
		return !stack.isEmpty();
	}
	
	/**
	 * Called on server only when a default projectile (or one that extends it) successfully damages a LivingEntity (so after damage).
	 * <br/>May change that later.
	 */
	default void onLivingEntityHit(BulletEntity projectile, LivingEntity target, @Nullable Entity shooter, Level level) {}
	
	/**
	 * Called on server when a default projectile (or one that extends it) hits a block, right before it's removed.
	 * <br/>Noclipping bullets never call this.
	 */
	default void onBlockHit(BulletEntity projectile, BlockState block, @Nullable Entity shooter, Level level) {}
	
	/**
	 * Called on server only as damage is being applied when a bullet carrying this item hits. The target may not be a LivingEntity.
	 * <br/>May change that later.
	 */
	default double modifyDamage(double damage, BulletEntity projectile, Entity target, @Nullable Entity shooter, Level level) {
		return damage;
	}
	
	/**
	 * For bullet bag: if true, the gun will fire a different bullet than the actual current IBullet.
	 * <br/>Won't work recursively, no nesting bags here!
	 */
	default boolean hasDelegate(ItemStack stack, Player player) {
		return false;
	}
	
	/**
	 * When delegate is involved, gives the ItemStack that will be fired.
	 * <br/>The gun will fire that stack as if it were the original ammo, but it'll be consumed by the parent IBullet.
	 */
	default ItemStack getDelegate(ItemStack stack, Player player) {
		return stack;
	}
	

}