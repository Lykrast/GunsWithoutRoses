package xyz.kaleidiodev.kaleidiosguns.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.world.World;
import xyz.kaleidiodev.kaleidiosguns.config.KGConfig;
import xyz.kaleidiodev.kaleidiosguns.entity.BulletEntity;

import javax.annotation.Nullable;

public interface IBullet {
	/**
	 * Creates a projectile and set its stats and stuff. The gun will give it velocity and spawn it in the world.
	 */
	BulletEntity createProjectile(World world, ItemStack stack, LivingEntity shooter, boolean isPlasma);

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

	/**
	 * Called on server only when a default projectile (or one that extends it) sucessfully damages a LivingEntity (so after damage).
	 * <br/>May change that later.
	 */
	default void onLivingEntityHit(BulletEntity projectile, LivingEntity target, @Nullable Entity shooter, World world) {
		//if the chance to heal rolls successfully
		if (projectile.rollRewardChance()) {
			//calculate the damage the enemy recieved.
			float damageDelta = projectile.getHealthOfVictim() - target.getHealth();

			//heal the shooter by a fraction of what damage the enemy recieved.
			LivingEntity shooterEntity = (LivingEntity)shooter;
			//cast to primitive first before casting to float.  thanks forge.
			shooterEntity.setHealth(shooterEntity.getHealth() + (damageDelta * (float)(double) KGConfig.passionForBloodHealIncrease.get()));
		}
	}

	/**
	 * Called on server only as damage is being applied when a bullet carrying this item hits. The target may not be a LivingEntity.
	 * <br/>May change that later.
	 */
	default double modifyDamage(double damage, BulletEntity projectile, Entity target, @Nullable Entity shooter, World world) {
		//if puncturing enchantment is present.
		double newDamage = damage;
		if (projectile.getPuncturingAmount() > 0D){
			if (target instanceof LivingEntity) {
				LivingEntity victim = (LivingEntity)target;
				if (victim.getArmorValue() != 0) {
					//how many armor bars are full?
					double armorAssumption = (double) victim.getArmorValue() / 20;
					//total up new damage.
					double someDamage = damage * armorAssumption * projectile.getPuncturingAmount();
					newDamage += someDamage;
				}
			}
		}

		if (projectile.isCritical()) newDamage *= KGConfig.criticalDamage.get();
		return newDamage;
	}

}
