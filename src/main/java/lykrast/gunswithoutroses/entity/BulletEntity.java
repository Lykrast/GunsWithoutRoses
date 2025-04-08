package lykrast.gunswithoutroses.entity;

import lykrast.gunswithoutroses.item.IBullet;
import lykrast.gunswithoutroses.registry.GWRDamage;
import lykrast.gunswithoutroses.registry.GWREntities;
import lykrast.gunswithoutroses.registry.GWRItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class BulletEntity extends Projectile implements ItemSupplier {
	//from Fireball
	private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(BulletEntity.class, EntityDataSerializers.ITEM_STACK);

	protected double damage = 1;
	protected double knockbackStrength = 0;
	protected double headshotMult = 1;
	//same as fireballs but now it can be changed yay
	protected static final double DEFAULT_WATER_INERTIA = 0.8;
	protected double waterInertia = DEFAULT_WATER_INERTIA;
	protected int ticksSinceFired;

	public BulletEntity(EntityType<? extends BulletEntity> type, Level level) {
		super(type, level);
	}

	public BulletEntity(Level level, LivingEntity shooter) {
		this(GWREntities.BULLET.get(), shooter, level);
	}

	//These 2 from AbstractHurtingProjectile
	protected BulletEntity(EntityType<? extends BulletEntity> type, double x, double y, double z, Level level) {
		this(type, level);
		moveTo(x, y, z, getYRot(), getXRot());
		reapplyPosition();

	}
	protected BulletEntity(EntityType<? extends BulletEntity> type, LivingEntity shooter, Level level) {
		this(type, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ(), level);
		setOwner(shooter);
		setRot(shooter.getYRot(), shooter.getXRot());
	}

	private static final double STOP_TRESHOLD = 0.01;
	
	public double getWaterInertia() {
		return waterInertia;
	}
	
	//copied from ProjectileUtil.getHitResultOnMoveVector so that it can hit through blocks properly
	protected HitResult getHitResult() {
		Vec3 pos = position();
		Vec3 vel = getDeltaMovement();
		Vec3 nextpos = pos.add(vel);
		HitResult hitresult = level().clip(new ClipContext(pos, nextpos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		//don't clip to blocks if we're a noclip bullet
		if (!noPhysics && hitresult.getType() != HitResult.Type.MISS) nextpos = hitresult.getLocation();

		HitResult hitresult1 = ProjectileUtil.getEntityHitResult(level(), this, pos, nextpos, getBoundingBox().expandTowards(vel).inflate(1), this::canHitEntity);
		if (hitresult1 != null) {
			hitresult = hitresult1;
		}

		return hitresult;
	}
	
	//separated from the tick() for the piercing class to emulate piercing arrows (that can collide multiple times in one tick)
	protected void processCollision() {
		HitResult hitresult = getHitResult();
		if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
			onHit(hitresult);
		}
	}

	//deprecation for the hasChunkAt from abstracthurtingprojectile tick, no clue what you need to replace it with
	@SuppressWarnings({ "deprecation", "resource" })
	@Override
	public void tick() {
		//Using a thing I save so that bullets don't get clogged up on chunk borders
		ticksSinceFired++;
		if (ticksSinceFired > 100 || getDeltaMovement().lengthSqr() < STOP_TRESHOLD) {
			remove(RemovalReason.KILLED);
		}
		//super.tick() AbstractHurtingProjectile
		Entity entity = this.getOwner();
		if (level().isClientSide || (entity == null || !entity.isRemoved()) && level().hasChunkAt(blockPosition())) {
			super.tick();

			processCollision();

			checkInsideBlocks();
			Vec3 vec3 = getDeltaMovement();
			double nextx = getX() + vec3.x;
			double nexty = getY() + vec3.y;
			double nextz = getZ() + vec3.z;
			ProjectileUtil.rotateTowardsMovement(this, 0.2F);
			if (isInWater()) {
				for (int i = 0; i < 4; ++i) {
					level().addParticle(ParticleTypes.BUBBLE, nextx - vec3.x * 0.25, nexty - vec3.y * 0.25, nextz - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
				}

				setDeltaMovement(vec3.scale(getWaterInertia()));
			}

			level().addParticle(getTrailParticle(), nextx, nexty + 0.5, nextz, 0, 0, 0);
			setPos(nextx, nexty, nextz);
		}
		else {
			discard();
		}
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
		if (headshotMult > 1) compound.putDouble("hsmult", headshotMult);
		if (waterInertia != DEFAULT_WATER_INERTIA) compound.putDouble("waterinertia", waterInertia);
		//Fireball stuff
		ItemStack itemstack = this.getItemRaw();
		if (!itemstack.isEmpty()) compound.put("Item", itemstack.save(new CompoundTag()));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		ticksSinceFired = compound.getInt("tsf");
		damage = compound.getDouble("damage");
		if (compound.contains("knockback")) knockbackStrength = compound.getDouble("knockback");
		if (compound.contains("hsmult")) headshotMult = Math.max(1, compound.getDouble("hsmult"));
		if (compound.contains("waterinertia")) waterInertia = compound.getDouble("waterinertia");
		//Fireball stuff
		setItem(ItemStack.of(compound.getCompound("Item")));
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
	
	/**
	 * Damage multiplier when hitting the head. 1 or lower disables that detection.
	 */
	public void setHeadshotMultiplier(double headshotMult) {
		this.headshotMult = headshotMult;
	}
	
	public double getHeadshotMultiplier() {
		return headshotMult;
	}
	
	/**
	 * Velocity multiplier each tick in water. Default is 0.8
	 */
	public void setWaterInertia(double inertia) {
		this.waterInertia = inertia;
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
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	//Stuff de-inherited from Fireball
	public void setItem(ItemStack stack) {
		getEntityData().set(DATA_ITEM_STACK, stack.copyWithCount(1));
	}

	protected ItemStack getItemRaw() {
		return getEntityData().get(DATA_ITEM_STACK);
	}

	@Override
	public ItemStack getItem() {
		ItemStack itemstack = this.getItemRaw();
		return itemstack.isEmpty() ? new ItemStack(GWRItems.ironBullet.get()) : itemstack;
	}

	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
	}
	
	//de-inherited from AbstractHurtingProjectile
	@Override
	public boolean shouldRenderAtSqrDistance(double sqrDistance) {
		double d0 = getBoundingBox().getSize() * 4;
		if (Double.isNaN(d0)) d0 = 4;
		d0 *= 64;
		return sqrDistance < d0 * d0;
	}

	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.SMOKE;
	}

	@Override
	public float getLightLevelDependentMagicValue() {
		return 1.0F;
	}

}
