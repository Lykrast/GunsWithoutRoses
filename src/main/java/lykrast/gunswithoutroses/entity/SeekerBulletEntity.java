package lykrast.gunswithoutroses.entity;

import lykrast.gunswithoutroses.registry.GWREntities;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;

public class SeekerBulletEntity extends BulletEntity {
	private static final double RANGE = 8, RANGESQR = RANGE * RANGE;
	//whether it did the smart bounce or not, synched cause otherwise client would remove on hit
	private static final EntityDataAccessor<Boolean> HAS_BOUNCED = SynchedEntityData.defineId(SeekerBulletEntity.class, EntityDataSerializers.BOOLEAN);
	private boolean bouncedThisTick = false;
	//how much damage is multiplied after the bounce
	protected double bounceMult;

	public SeekerBulletEntity(EntityType<? extends BulletEntity> type, Level level) {
		super(type, level);
	}

	public SeekerBulletEntity(Level level, LivingEntity shooter) {
		super(GWREntities.BULLET_SEEKER.get(), shooter, level);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		getEntityData().define(HAS_BOUNCED, false);
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
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		//noclipping bullets don't interact with blocks
		if (noPhysics) return;
		if (hasBounced()) remove(RemovalReason.KILLED);
		else {
			bouncedThisTick = true;
			setHasBounced(true);
			damage *= bounceMult;
			setPos(result.getLocation());
			if (!seekTarget(result.getDirection())) {
				//normal bounce if no target found
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
			}
		}
	}

	protected boolean seekTarget(Direction hit) {
		//if (level().isClientSide()) return false;
		//we won't hit blocks if we are noclipping, and since we hit a block we should find something away
		AABB bb = getBoundingBox().inflate(RANGE + 1);
		switch (hit) {
			case DOWN:
				//hit towards negative y
				bb.setMinY(getY());
				break;
			case UP:
				//hit towards positive y
				bb.setMaxY(getY());
				break;
			case NORTH:
				//hit towards negative z
				bb.setMinZ(getZ());
				break;
			case SOUTH:
				//hit towards positive z
				bb.setMaxZ(getZ());
				break;
			case WEST:
				//hit towards negative x
				bb.setMinX(getX());
				break;
			case EAST:
				//hit towards positive x
				bb.setMaxX(getX());
				break;
		}
		LivingEntity closest = null;
		double closestDist = -1;
		Vec3 closestAim = null;
		Entity shooter = getOwner(); //could be null
		Vec3 pos = position();
		for (LivingEntity ent : level().getEntitiesOfClass(LivingEntity.class, bb)) {
			//want closest mob that has line of sight from the bullet
			if (!ent.isAlive() || ent == shooter || !canHitEntity(ent) || (shooter != null && ent.isAlliedTo(shooter))) continue;
			//aim for centermass, midway between bottom (y) and eyes
			Vec3 centermass = ent.position().add(0, (ent.getEyeY() - ent.getY()) / 2.0, 0);
			double dsqr = pos.distanceToSqr(centermass);
			//don't bother if we already have something closer or we out of range (as the check is in a rectangle and we have sphere range)
			if (dsqr > (closest != null ? closestDist : RANGESQR)) continue;
			//check for blocks in the way
			HitResult los = level().clip(new ClipContext(pos, centermass, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			if (los.getType() != Type.MISS) continue;
			closestDist = dsqr;
			closest = ent;
			closestAim = centermass;
		}

		//if we had something aim toward it
		if (closest != null) {
			double speed = getDeltaMovement().length();
			Vec3 newdelta = closestAim.subtract(pos).normalize().scale(speed);
			setDeltaMovement(newdelta);
			//TODO custom sound event
			playSound(SoundEvents.SHULKER_BOX_OPEN, 1, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1) / 0.8F);

			//if we hit enemy on next tick it's hard to see how the bounce went
			//ideally I'd love bullet trails but eh that'll work for now
			for (int i = 1; i < 4; i++) {
				level().addParticle(getTrailParticle(), getX() + i*newdelta.x/4, getY() + i*newdelta.y/4 + 0.5, getZ() + i*newdelta.z/4, 0, 0, 0);
			}
			return true;
		}
		else return false;
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.END_ROD;
	}

	@Override
	protected boolean shouldDespawnOnHit(HitResult result) {
		//smart bounces off one block, so if hits an entity first it just gone
		return result.getType() != HitResult.Type.BLOCK || !hasBounced();
	}

	public void setHasBounced(boolean hasBounced) {
		getEntityData().set(HAS_BOUNCED, hasBounced);
	}

	public boolean hasBounced() {
		return getEntityData().get(HAS_BOUNCED);
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
		compound.putBoolean("hasbounced", hasBounced());
		compound.putFloat("bounceMult", (float) bounceMult);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setHasBounced(compound.getBoolean("hasbounced"));
		bounceMult = compound.getFloat("bounceMult");
	}

}
