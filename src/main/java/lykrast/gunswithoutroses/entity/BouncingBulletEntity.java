package lykrast.gunswithoutroses.entity;

import java.util.Optional;

import lykrast.gunswithoutroses.registry.GWREntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BouncingBulletEntity extends BulletEntity {
	//remaining bounces, synced so that the client can render it properly
	private static final EntityDataAccessor<Integer> BOUNCES = SynchedEntityData.defineId(BouncingBulletEntity.class, EntityDataSerializers.INT);
	//how much damage is multiplied with each bounce
	protected double bounceMult;
	//if we bounce off a mob, can't hit it again until next bounce
	private boolean ignoreMob = false;
	private int ignoreMobId;
	private boolean bouncedThisTick = false;

	public BouncingBulletEntity(EntityType<? extends BulletEntity> type, Level level) {
		super(type, level);
	}

	public BouncingBulletEntity(Level level, LivingEntity shooter) {
		super(GWREntities.BULLET_BOUNCING.get(), shooter, level);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		getEntityData().define(BOUNCES, 0);
	}

	@Override
	protected void processCollision() {
		bouncedThisTick = false;
		super.processCollision();
	}

	@Override
	protected void move() {
		//as the bounce moves to hit location, cut the normal movement if we hit a bounce
		if (!bouncedThisTick) super.move();
	}

	@Override
	protected boolean canHitEntity(Entity target) {
		return super.canHitEntity(target) && (!ignoreMob || target.getId() != ignoreMobId);
	}

	@Override
	protected void onHitEntity(EntityHitResult raytrace) {
		super.onHitEntity(raytrace);
		int bounces = getBounces();
		if (bounces <= 0) remove(RemovalReason.KILLED);
		else {
			bouncedThisTick = true;
			setBounces(bounces - 1);
			damage *= bounceMult;
			ignoreMob = true;
			ignoreMobId = raytrace.getEntity().getId();
			//calculate impact location by redoing the projectile collision check kinda
			//since the hitresult gives the entity's position
			Vec3 pos = position();
			Vec3 nextpos = pos.add(getDeltaMovement());
			Optional<Vec3> hit = raytrace.getEntity().getBoundingBox().inflate(0.3).clip(pos, nextpos);
			if (hit.isPresent()) setPos(hit.get());
			
			setDeltaMovement(getDeltaMovement().reverse());
			slimeEffects();
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		//noclipping bullets don't interact with blocks
		if (noPhysics) return;
		super.onHitBlock(result);
		int bounces = getBounces();
		System.out.println(bounces);
		if (bounces <= 0) remove(RemovalReason.KILLED);
		else {
			bouncedThisTick = true;
			setBounces(bounces - 1);
			damage *= bounceMult;
			ignoreMob = false;
			setPos(result.getLocation());
			switch (result.getDirection().getAxis()) {
				case X:
					setDeltaMovement(getDeltaMovement().multiply(-1, 1, 1));
					break;
				case Y:
					setDeltaMovement(getDeltaMovement().multiply(1, -1, 1));
					break;
				case Z:
					setDeltaMovement(getDeltaMovement().multiply(1, 1, -1));
					break;
			}
			slimeEffects();
		}
	}

	protected void slimeEffects() {
		//copied from slimes
		for (int j = 0; j < 8; ++j) {
			float angle = random.nextFloat() * Mth.TWO_PI;
			float dist = random.nextFloat() * 0.5F + 0.5F;
			float offX = Mth.sin(angle) * 0.5F * dist;
			float offZ = Mth.cos(angle) * 0.5F * dist;
			level().addParticle(ParticleTypes.ITEM_SLIME, this.getX() + offX, this.getY(), this.getZ() + offZ, 0, 0, 0);
		}
		playSound(SoundEvents.SLIME_SQUISH_SMALL, 1, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1) / 0.8F);
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.ITEM_SLIME;
	}

	@Override
	protected boolean shouldDespawnOnHit(HitResult result) {
		//this gets checked after the onhit that will decrement the bounce counter
		//so we checking for -1 instead of 1
		return getBounces() < 0;
	}

	public void setBounces(int bounces) {
		getEntityData().set(BOUNCES, bounces);
	}

	public int getBounces() {
		return getEntityData().get(BOUNCES);
	}

	public void setBounceMultiplier(double bounceMult) {
		this.bounceMult = bounceMult;
	}

	public double getBounceMultiplier() {
		return bounceMult;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("bounces", getBounces());
		compound.putFloat("bounceMult", (float) bounceMult);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setBounces(compound.getInt("bounces"));
		bounceMult = compound.getFloat("bounceMult");
	}

}
