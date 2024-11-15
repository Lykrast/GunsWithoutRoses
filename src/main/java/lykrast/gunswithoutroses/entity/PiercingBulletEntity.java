package lykrast.gunswithoutroses.entity;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class PiercingBulletEntity extends BulletEntity {
	//TODO AAAAA JANKY COLLISION IT ONLY HITS ONCE PER TICK
	//number of entities it can still go through
	protected int pierce;
	//copied that stuff from arrows
	@Nullable
	private IntOpenHashSet piercingIgnoreEntityIds;

	public PiercingBulletEntity(EntityType<? extends BulletEntity> type, Level level) {
		super(type, level);
	}

	public PiercingBulletEntity(Level level, LivingEntity shooter) {
		super(level, shooter);
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
		System.out.println(piercingIgnoreEntityIds.size());
	}
	
	@Override
	protected boolean shouldDespawnOnHit(HitResult result) {
		//pierce of 1 means pierce the first and disappear on second, and since this is after adding the target to the hash that should be good
		if (result.getType() == HitResult.Type.ENTITY) return (piercingIgnoreEntityIds != null && piercingIgnoreEntityIds.size() > pierce);
		else return true;
	}

	public void setPierce(int pierce) {
		this.pierce = pierce;
	}

	public int getPierce() {
		return pierce;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("pierce", pierce);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		pierce = compound.getInt("pierce");
	}

}
