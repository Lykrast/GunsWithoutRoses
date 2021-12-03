package lykrast.gunswithoutroses.entity;

import lykrast.gunswithoutroses.item.IBullet;
import lykrast.gunswithoutroses.registry.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BulletEntity extends AbstractFireballEntity {
	protected double damage = 1;
	protected boolean ignoreInvulnerability = false;
	protected double knockbackStrength = 0;
	protected int ticksSinceFired;

	public BulletEntity(EntityType<? extends BulletEntity> p_i50160_1_, World p_i50160_2_) {
		super(p_i50160_1_, p_i50160_2_);
	}

	public BulletEntity(World worldIn, LivingEntity shooter) {
		this(worldIn, shooter, 0, 0, 0);
		setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
	}

	public BulletEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(ModEntities.BULLET, shooter, accelX, accelY, accelZ, worldIn);
	}

	public BulletEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(ModEntities.BULLET, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	private static final double STOP_TRESHOLD = 0.01;

	@Override
	public void tick() {
		//Using a thing I save so that bullets don't get clogged up on chunk borders
		ticksSinceFired++;
		if (ticksSinceFired > 100 || getDeltaMovement().lengthSqr() < STOP_TRESHOLD) {
			remove();
		}
		super.tick();
	}

	@Override
	protected void onHitEntity(EntityRayTraceResult raytrace) {
		super.onHitEntity(raytrace);
		if (!level.isClientSide) {
			Entity target = raytrace.getEntity();
			Entity shooter = getOwner();
			IBullet bullet = (IBullet) getItemRaw().getItem();
			
			if (isOnFire()) target.setSecondsOnFire(5);
			int lastHurtResistant = target.invulnerableTime;
			if (ignoreInvulnerability) target.invulnerableTime = 0;
			boolean damaged = target.hurt((new IndirectEntityDamageSource("arrow", this, shooter)).setProjectile(), (float) bullet.modifyDamage(damage, this, target, shooter, level));
			
			if (damaged && target instanceof LivingEntity) {
				LivingEntity livingTarget = (LivingEntity)target;
				if (knockbackStrength > 0) {
					double actualKnockback = knockbackStrength;
					//Knocback amplifying potion from Hanami TODO replace once it's in another mod
					//if (Holders.Hanami.INSTABILITY != null && livingTarget.isPotionActive(Holders.Hanami.INSTABILITY)) actualKnockback *= 2 + livingTarget.getActivePotionEffect(Holders.Hanami.INSTABILITY).getAmplifier();
					
					Vector3d vec = getDeltaMovement().multiply(1, 0, 1).normalize().scale(actualKnockback);
					if (vec.lengthSqr() > 0) livingTarget.push(vec.x, 0.1, vec.z);
				}

				if (shooter instanceof LivingEntity) doEnchantDamageEffects((LivingEntity)shooter, target);
				
				bullet.onLivingEntityHit(this, livingTarget, shooter, level);
			}
			else if (!damaged && ignoreInvulnerability) target.invulnerableTime = lastHurtResistant;
		}
	}

	@Override
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		//Don't disappear on blocks if we're set to noclipping
		if (!level.isClientSide && (!noPhysics || result.getType() != RayTraceResult.Type.BLOCK)) remove();
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("tsf", ticksSinceFired);
		compound.putDouble("damage", damage);
		if (ignoreInvulnerability) compound.putBoolean("ignoreinv", ignoreInvulnerability);
		if (knockbackStrength != 0) compound.putDouble("knockback", knockbackStrength);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		ticksSinceFired = compound.getInt("tsf");
		damage = compound.getDouble("damage");
		//The docs says if it's not here it's gonna be false/0 so it should be good
		ignoreInvulnerability = compound.getBoolean("ignoreinv");
		knockbackStrength = compound.getDouble("knockback");
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getDamage() {
		return damage;
	}

	public void setIgnoreInvulnerability(boolean ignoreInvulnerability) {
		this.ignoreInvulnerability = ignoreInvulnerability;
	}

	/**
	 * Knockback on impact, 0.6 is equivalent to Punch I.
	 */
	public void setKnockbackStrength(double knockbackStrength) {
		this.knockbackStrength = knockbackStrength;
	}

	@Override
	public boolean isPickable() {
		return false;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return false;
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Override
	protected float getInertia() {
		return 1;
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
