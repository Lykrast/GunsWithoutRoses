package lykrast.gunswithoutroses.entity;

import lykrast.gunswithoutroses.item.IBullet;
import lykrast.gunswithoutroses.registry.GWRDamage;
import lykrast.gunswithoutroses.registry.GWREntities;
import lykrast.gunswithoutroses.registry.GWRItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class BulletEntity extends Fireball {
	protected double damage = 1;
	protected double knockbackStrength = 0;
	protected double headshotMult = 1;
	protected int ticksSinceFired;

	public BulletEntity(EntityType<? extends BulletEntity> type, Level level) {
		super(type, level);
	}

	public BulletEntity(Level worldIn, LivingEntity shooter) {
		this(worldIn, shooter, 0, 0, 0);
		setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
	}

	public BulletEntity(Level level, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(GWREntities.BULLET.get(), shooter, accelX, accelY, accelZ, level);
	}

	public BulletEntity(Level level, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(GWREntities.BULLET.get(), x, y, z, accelX, accelY, accelZ, level);
	}

	private static final double STOP_TRESHOLD = 0.01;

	@Override
	public void tick() {
		//Using a thing I save so that bullets don't get clogged up on chunk borders
		ticksSinceFired++;
		if (ticksSinceFired > 100 || getDeltaMovement().lengthSqr() < STOP_TRESHOLD) {
			remove(RemovalReason.KILLED);
		}
		super.tick();
	}
	
	protected boolean hasHeadshot(Entity target) {
		if (headshotMult <= 1) return false;
		Vec3 from = position();
		Vec3 to = from.add(getDeltaMovement());
		//get entity collision that's above the eyes and see if the movement hits it
		//similar check as ProjectileUtil.getEntityHitResult (with the inflate 0.3)
		return target.getBoundingBox().setMinY(target.getEyeY()).inflate(0.3).clip(from, to).isPresent();
	}

	@SuppressWarnings("resource")
	@Override
	protected void onHitEntity(EntityHitResult raytrace) {
		//note: that super is currently empty
		super.onHitEntity(raytrace);
		if (!level().isClientSide) {
			Entity target = raytrace.getEntity();
			Entity shooter = getOwner();
			Item item = getItemRaw().getItem();
			IBullet bullet = item instanceof IBullet ? (IBullet) item : GWRItems.ironBullet.get();
			
			if (isOnFire()) target.setSecondsOnFire(5);
			int lastHurtResistant = target.invulnerableTime;
			target.invulnerableTime = 0;
			boolean headshot = hasHeadshot(target);
			float hitdamage = (float)bullet.modifyDamage(damage * (headshot ? headshotMult : 1), this, target, shooter, level(), headshot);
			boolean damaged = shooter == null
					? target.hurt(GWRDamage.gunDamage(level().registryAccess(), this), hitdamage)
					: target.hurt(GWRDamage.gunDamage(level().registryAccess(), this, shooter), hitdamage);
			
			if (damaged && target instanceof LivingEntity) {
				LivingEntity livingTarget = (LivingEntity)target;
				if (knockbackStrength > 0) {
					double actualKnockback = knockbackStrength;
					//Knocback amplifying potion from Hanami
					//if (Holders.Hanami.INSTABILITY != null && livingTarget.isPotionActive(Holders.Hanami.INSTABILITY)) actualKnockback *= 2 + livingTarget.getActivePotionEffect(Holders.Hanami.INSTABILITY).getAmplifier();
					
					//Punch I is 0.6, Punch II is 1.2, so we do the adjusting here to match that scale
					Vec3 vec = getDeltaMovement().multiply(1, 0, 1).normalize().scale(actualKnockback*0.6);
					if (vec.lengthSqr() > 0) livingTarget.push(vec.x, 0.1, vec.z);
				}

				if (shooter instanceof LivingEntity) doEnchantDamageEffects((LivingEntity)shooter, target);
				
				bullet.onLivingEntityHit(this, livingTarget, shooter, level(), headshot);
			}
			else if (!damaged) target.invulnerableTime = lastHurtResistant;
		}
	}
	
	@SuppressWarnings("resource")
	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		//Don't disappear on blocks if we're set to noclipping
		if (!level().isClientSide && (!noPhysics || result.getType() != HitResult.Type.BLOCK) && shouldDespawnOnHit(result)) remove(RemovalReason.KILLED);
	}
	
	/**
	 * Called on server on an impact after the onhitentity/block to know if should be removed or not.
	 * <br>Will not be called when a noclipping bullet hits a block.
	 * <br>Intended to be overridden for like piercing or bouncy bullets.
	 */
	protected boolean shouldDespawnOnHit(HitResult result) {
		return true;
	}

	@SuppressWarnings("resource")
	@Override
	protected void onHitBlock(BlockHitResult result) {
		//noclipping bullets don't interact with blocks
		if (noPhysics) return;
		super.onHitBlock(result);		
		if (!level().isClientSide) {
			IBullet bullet = getItemRaw().getItem() instanceof IBullet ? (IBullet) getItemRaw().getItem() : GWRItems.ironBullet.get();
			bullet.onBlockHit(this, result, getOwner(), level());
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("tsf", ticksSinceFired);
		compound.putDouble("damage", damage);
		if (knockbackStrength != 0) compound.putDouble("knockback", knockbackStrength);
		compound.putDouble("hsmult", headshotMult);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		ticksSinceFired = compound.getInt("tsf");
		damage = compound.getDouble("damage");
		knockbackStrength = compound.getDouble("knockback");
		headshotMult = Math.max(1, compound.getDouble("hsmult"));
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getDamage() {
		return damage;
	}

	@Override
	//Same as normal but ignores player velocity
	//Also I finally found why it takes floats, cause the trig functions take floats :(
	public void shootFromRotation(Entity shooter, float xRot, float yRot, float upArc, float speed, float spread) {
		float f = -Mth.sin(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
		float f1 = -Mth.sin((xRot + upArc) * Mth.DEG_TO_RAD);
		float f2 = Mth.cos(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
		shoot((double) f, (double) f1, (double) f2, speed, spread);
		//Vec3 vec3 = shooter.getDeltaMovement();
		//this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, shooter.isOnGround() ? 0.0D : vec3.y, vec3.z));
	}

	/**
	 * Knockback on impact, 1 is equivalent to Punch I.
	 */
	public void setKnockbackStrength(double knockbackStrength) {
		this.knockbackStrength = knockbackStrength;
	}

	/**
	 * Knockback on impact, 1 is equivalent to Punch I.
	 */
	public double getKnockbackStrength() {
		return knockbackStrength;
	}
	
	public void setHeadshotMultiplier(double headshotMult) {
		this.headshotMult = headshotMult;
	}
	
	public double getHeadshotMultiplier() {
		return headshotMult;
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
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
