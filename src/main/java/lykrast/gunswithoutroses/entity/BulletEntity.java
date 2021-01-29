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
		setPosition(shooter.getPosX(), shooter.getPosYEye() - 0.1, shooter.getPosZ());
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
		if (ticksSinceFired > 100 || getMotion().lengthSquared() < STOP_TRESHOLD) {
			remove();
		}
		super.tick();
	}

	@Override
	protected void onEntityHit(EntityRayTraceResult raytrace) {
		super.onEntityHit(raytrace);
		if (!world.isRemote) {
			Entity target = raytrace.getEntity();
			Entity shooter = func_234616_v_();
			if (isBurning()) target.setFire(5);
			int lastHurtResistant = target.hurtResistantTime;
			if (ignoreInvulnerability) target.hurtResistantTime = 0;
			boolean damaged = target.attackEntityFrom((new IndirectEntityDamageSource("arrow", this, shooter)).setProjectile(), (float) damage);
			if (damaged && target instanceof LivingEntity) {
				LivingEntity livingTarget = (LivingEntity)target;
				if (knockbackStrength > 0) {
					double actualKnockback = knockbackStrength;
					//Knocback amplifying potion from Hanami TODO replace once it's in another mod
					//if (Holders.Hanami.INSTABILITY != null && livingTarget.isPotionActive(Holders.Hanami.INSTABILITY)) actualKnockback *= 2 + livingTarget.getActivePotionEffect(Holders.Hanami.INSTABILITY).getAmplifier();
					
					Vector3d vec = getMotion().mul(1, 0, 1).normalize().scale(actualKnockback);
					if (vec.lengthSquared() > 0) livingTarget.addVelocity(vec.x, 0.1, vec.z);
				}

				if (shooter instanceof LivingEntity) applyEnchantments((LivingEntity)shooter, target);
				
				IBullet bullet = (IBullet) getStack().getItem();
				bullet.onLivingEntityHit(this, livingTarget, shooter, world);
			}
			else if (!damaged && ignoreInvulnerability) target.hurtResistantTime = lastHurtResistant;
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		//Don't disappear on blocks if we're set to noclipping
		if (!world.isRemote && (!noClip || result.getType() != RayTraceResult.Type.BLOCK)) remove();
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("tsf", ticksSinceFired);
		compound.putDouble("damage", damage);
		if (ignoreInvulnerability) compound.putBoolean("ignoreinv", ignoreInvulnerability);
		if (knockbackStrength != 0) compound.putDouble("knockback", knockbackStrength);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
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
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}

	@Override
	protected boolean isFireballFiery() {
		return false;
	}

	@Override
	protected float getMotionFactor() {
		return 1;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
