package lykrast.gunswithoutroses.item;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.entity.BulletEntity;
import lykrast.gunswithoutroses.registry.GWRSounds;
import net.minecraft.sounds.SoundEvents;
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
	 * @deprecated {@link lykrast.gunswithoutroses.item.IBullet#onLivingEntityHit(BulletEntity, LivingEntity, Entity, Level, boolean) Use headshot sensitive version, will remove this in 1.21}
	 */
	@Deprecated
	default void onLivingEntityHit(BulletEntity projectile, LivingEntity target, @Nullable Entity shooter, Level level) {}
	
	/**
	 * Called on server only when a default projectile (or one that extends it) successfully damages a LivingEntity (so after damage).
	 * <br/>This is where the default headshot sound effect happens, so call super if you don't want to redo them.
	 */
	default void onLivingEntityHit(BulletEntity projectile, LivingEntity target, @Nullable Entity shooter, Level level, boolean headshot) {
		//TODO find a sound
		if (headshot) level.playSound(null, projectile.getX(), projectile.getY(), projectile.getZ(), SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, shooter != null ? shooter.getSoundSource() : projectile.getSoundSource(), 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
		onLivingEntityHit(projectile, target, shooter, level);
	}

	/**
	 * Called on server when a default projectile (or one that extends it) hits a block, right before it's removed.
	 * <br/>Noclipping bullets never call this.
	 */
	default void onBlockHit(BulletEntity projectile, BlockState block, @Nullable Entity shooter, Level level) {}
	
	/**
	 * @deprecated {@link lykrast.gunswithoutroses.item.IBullet#modifyDamage(double, BulletEntity, Entity, Entity, boolean) Use headshot sensitive version, will remove this in 1.21}
	 */
	@Deprecated
	default double modifyDamage(double damage, BulletEntity projectile, Entity target, @Nullable Entity shooter, Level level) {
		return damage;
	}
	
	/**
	 * Called on server only as damage is being applied when a bullet carrying this item hits. The target may not be a LivingEntity.
	 * <br/>Headshot multiplier has already been applied when this is called (if applicable).
	 */
	default double modifyDamage(double damage, BulletEntity projectile, Entity target, @Nullable Entity shooter, Level level, boolean headshot) {
		return modifyDamage(damage, projectile, target, shooter, level);
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