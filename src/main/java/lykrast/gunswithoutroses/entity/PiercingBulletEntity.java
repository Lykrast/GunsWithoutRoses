package lykrast.gunswithoutroses.entity;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import lykrast.gunswithoutroses.registry.GWREntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class PiercingBulletEntity extends BulletEntity {
	//number of entities it can still go through
	protected int pierce;
	//how much damage is multiplied by after each pierce
	protected double pierceMult;
	//copied that stuff from arrows
	@Nullable
	private IntOpenHashSet piercingIgnoreEntityIds;

	public PiercingBulletEntity(EntityType<? extends BulletEntity> type, Level level) {
		super(type, level);
	}

	public PiercingBulletEntity(Level level, LivingEntity shooter) {
		super(GWREntities.BULLET_PIERCING.get(), shooter, level);
	}
	
	@Override
	protected void processCollision() {
		//do it like arrows, loop while we're hitting entities so we can pierce in a single tick
		while (!isRemoved()) {
			HitResult hitresult = getHitResult();
			if (hitresult.getType() != HitResult.Type.MISS) {
				if (net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) break;
				onHit(hitresult);
			}
			if (hitresult.getType() != HitResult.Type.ENTITY) break;
		}
	}

	@Override
	protected boolean canHitEntity(Entity target) {
		return super.canHitEntity(target) && (piercingIgnoreEntityIds == null || !piercingIgnoreEntityIds.contains(target.getId()));
	}
	
	@Override
	protected void onHitEntity(EntityHitResult raytrace) {
		super.onHitEntity(raytrace);
		if (piercingIgnoreEntityIds == null) piercingIgnoreEntityIds = new IntOpenHashSet(5);
		piercingIgnoreEntityIds.add(raytrace.getEntity().getId());
		damage *= pierceMult;
		//pierce of 1 means pierce the first and disappear on second, and since this is after adding the target to the hash that should be good
		if (piercingIgnoreEntityIds.size() > pierce) remove(RemovalReason.KILLED);
	}
	
	@Override
	protected boolean shouldDespawnOnHit(HitResult result) {
		//the removal is handlded on the onhitentity but we still need to check here
		if (result.getType() == HitResult.Type.ENTITY) return (piercingIgnoreEntityIds != null && piercingIgnoreEntityIds.size() > pierce);
		else return true;
	}

	public void setPierce(int pierce) {
		this.pierce = pierce;
	}

	public int getPierce() {
		return pierce;
	}

	public void setPierceMultiplier(double pierceMult) {
		this.pierceMult = pierceMult;
	}

	public double getPierceMultiplier() {
		return pierceMult;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("pierce", pierce);
		compound.putFloat("pierceMult", (float)pierceMult);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		pierce = compound.getInt("pierce");
		pierceMult = compound.getFloat("pierceMult");
	}

}
