package xyz.kaleidiodev.kaleidiosguns.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.ForgeHooks;
import xyz.kaleidiodev.kaleidiosguns.config.KGConfig;
import xyz.kaleidiodev.kaleidiosguns.item.GunItem;
import xyz.kaleidiodev.kaleidiosguns.item.IBullet;
import xyz.kaleidiodev.kaleidiosguns.network.NetworkUtils;
import xyz.kaleidiodev.kaleidiosguns.registry.ModEntities;
import xyz.kaleidiodev.kaleidiosguns.registry.ModItems;
import xyz.kaleidiodev.kaleidiosguns.registry.ModSounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BulletEntity extends AbstractFireballEntity {
	protected double damage = 1;
	protected double inaccuracy = 0.0;
	protected boolean ignoreInvulnerability = false;
	protected double knockbackStrength = 0.0;
	protected int ticksSinceFired;
	protected double healthRewardChance = 0.0f;
	protected float healthOfVictim;
	protected boolean shouldBreakBlock;
	protected boolean shouldCollateral;
	protected double bulletSpeed;
	public boolean isTorpedo;
	protected boolean shouldGlow;
	public boolean isCritical;
	protected GunItem shootingGun;
	protected Vector3d origin;
	public boolean shouldCombo;
	public boolean isExplosive;
	public boolean isPlasma;
	public double frostyDistance;
	public boolean isWither;
	public boolean wasRevenge;
	public boolean wasDark;
	public boolean isClean;
	public boolean isCorrupted;
	public int redstoneLevel;

	public BulletEntity(EntityType<? extends BulletEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	public BulletEntity(World worldIn, LivingEntity shooter) {
		this(worldIn, shooter, 0, 0, 0);
		setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
	}

	public BulletEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(ModEntities.BULLET, shooter, accelX, accelY, accelZ, worldIn);
		this.setNoGravity(true);
	}

	//change the particle type the projectile is going to emit
	@Override
	protected IParticleData getTrailParticle() {
		//seems that this method fires once on server and once on client.  something needs to be done in order to support multiple particle types
		if (isCritical) return ParticleTypes.ENCHANTED_HIT;
		if (isExplosive) return ParticleTypes.POOF;
		if (isPlasma) return ParticleTypes.INSTANT_EFFECT;
		if (wasRevenge) return ParticleTypes.HAPPY_VILLAGER;
		if (wasDark) return ParticleTypes.SMOKE;
		return ParticleTypes.CRIT;
	}

	@Override
	public void tick() {
		//Using a thing I save so that bullets don't get clogged up on chunk borders
		ticksSinceFired++;
		if (ticksSinceFired > 100) {
			remove();
		}

		this.setGlowing(shouldGlow);

		//completely rewrite the entity code here
		Entity entity = this.getOwner();
		if (this.level.isClientSide || (entity == null || !entity.removed) && this.level.hasChunkAt(this.blockPosition())) {
			if (!this.level.isClientSide) {
				this.setSharedFlag(6, this.isGlowing());
			}
			//note that "is away from owner" is absolutely useless anyway, so it was not included
			this.baseTick();

			RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
			if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
				this.onHit(raytraceresult);
			}

			//why rotate toward movement?  that makes no sense
			//this needs rewritten so that:
			//1. the rotation is correct on spawn before first tick
			//2. the rotation is always correct at any rotation speed, like an arrow
			this.checkInsideBlocks();
			ProjectileHelper.rotateTowardsMovement(this, 1.0F);

			//add support for torpedo enchantment
			float f = this.getInertia();
			Vector3d vector3d = this.getDeltaMovement();

			if (this.isInWater()) {
				this.level.addParticle(ParticleTypes.BUBBLE, this.getBoundingBox().getCenter().x, this.getBoundingBox().getCenter().y, this.getBoundingBox().getCenter().z, vector3d.x, vector3d.y, vector3d.z);
				//don't decrease inertia if the torpedo enchantment was on the gun
				if (!this.isTorpedo) f = 0.5f;
			}

			this.setDeltaMovement(vector3d.add(this.xPower, this.yPower, this.zPower).scale((double) f));
			//summon the particles in the center of the projectile instead of above it.
			//disable emitters when underwater, as otherwise it looks messy to have two emitters (bubble emitter happens elsewhere)
			if (!this.isInWater() && ticksSinceFired > 1 && this.level.isClientSide())
				this.level.addParticle(this.getTrailParticle(), this.getBoundingBox().getCenter().x, this.getBoundingBox().getCenter().y, this.getBoundingBox().getCenter().z, 0.0D, 0.0D, 0.0D);
			this.setPos(this.getX() + vector3d.x, this.getY() + vector3d.y, this.getZ() + vector3d.z);
		} else {
			this.remove();
		}
	}

	@Override
	protected void onHitEntity(EntityRayTraceResult raytrace) {
		super.onHitEntity(raytrace); //this seems to be on the right track, but we also need a manual raytrace to get a full list of entities in the next delta, just in case the projectile is moving too fast

		if (!level.isClientSide) {
			Entity target = raytrace.getEntity();
			entityHitProcess(target);
		}

		if (shouldCollateral) {
			//put some code here for the manual raytrace
			//the raytrace needs to be from current position to delta from last known position
			List<Entity> entities = new ArrayList<Entity>();
			AxisAlignedBB bb = this.getBoundingBox();
			Vector3d incPosition = new Vector3d(this.getDeltaMovement().x / (bulletSpeed * 10), this.getDeltaMovement().y / (bulletSpeed * 10), this.getDeltaMovement().z / (bulletSpeed * 10));

			//the raytrace is really just a bunch of steps for boundary boxes.  this means accelerator makes sniper collateral further
			for (double i = 0; i < this.bulletSpeed; i += 0.1) {
				bb = bb.move(incPosition);
				List<Entity> nextEntities = this.level.getEntities(this, bb);

				//don't bother adding entities to the list that are already there.
				for (Entity entity : nextEntities) {
					//that entity doesn't exist in the array, so add it
					if (!entities.contains(entity)) {
						entities.add(entity);
					}
				}

				//kill trace early if we hit a tile doing this, so it doesn't trace through walls.
				BlockPos someBlockPos = new BlockPos(bb.getCenter());
				BlockState someBlockState = this.level.getBlockState(someBlockPos);
				if ((someBlockState.getBlock() != Blocks.AIR) && !(someBlockState.getBlockState().is(BlockTags.FLOWERS)) && !(someBlockState.getBlockState().is(BlockTags.TALL_FLOWERS)) && !(someBlockState.getBlockState().is(BlockTags.SMALL_FLOWERS)))
					break;
			}

			//because the sniper cannot have a projectile ignore invulnerability anyway, this is safe to do.
			for (Entity entity : entities) {
				if (!(entity instanceof PlayerEntity) && (entity instanceof LivingEntity)) entityHitProcess(entity);
			}

		}
	}

	@Override
	protected void onHitBlock(BlockRayTraceResult raytrace) {
		//reset combo by sending owner player's UUID, which can never get damaged
		if ((getOwner() instanceof PlayerEntity) && !level.isClientSide())
			if (shootingGun != null) this.shootingGun.tryComboCalculate(getOwner().getUUID(), (PlayerEntity) getOwner());

		//make a spherical poof and a sound
		this.level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.impact, this.getSoundSource(), 0.25f, (random.nextFloat() * 0.5f) + 0.75f);
		double d0 = raytrace.getLocation().x();
		double d1 = raytrace.getLocation().y() + (this.getBoundingBox().getYsize() / 2);
		double d2 = raytrace.getLocation().z();
		this.level.addParticle(ParticleTypes.POOF, d0, d1, d2, 0.0D, 0.0D, 0.0D);

		if (shouldBreakBlock) {
			//test if the block is of the right tool type to mine with.
			//we could not guarantee the projectile ended up inside the block on this tick, so let's add some mathematics to work around that

			BlockPos blockPositionToMine = ((BlockRayTraceResult) raytrace).getBlockPos();
			ItemStack newTool;

			if (this.getDamage() > KGConfig.mineGunFifthLevel.get()) {
				newTool = new ItemStack(Items.DIAMOND_PICKAXE);
				tryBreakBlock(blockPositionToMine, newTool);
				newTool = new ItemStack(Items.DIAMOND_AXE);
				tryBreakBlock(blockPositionToMine, newTool);
				newTool = new ItemStack(Items.DIAMOND_SHOVEL);
				tryBreakBlock(blockPositionToMine, newTool);
				newTool = new ItemStack(Items.SHEARS);
				tryBreakBlock(blockPositionToMine, newTool);
				breakWeakBlocks(blockPositionToMine);
			} else if (this.getDamage() > KGConfig.mineGunFourthLevel.get()) {
				newTool = new ItemStack(Items.IRON_PICKAXE);
				tryBreakBlock(blockPositionToMine, newTool);
				newTool = new ItemStack(Items.IRON_AXE);
				tryBreakBlock(blockPositionToMine, newTool);
				newTool = new ItemStack(Items.IRON_SHOVEL);
				tryBreakBlock(blockPositionToMine, newTool);
				newTool = new ItemStack(Items.SHEARS);
				tryBreakBlock(blockPositionToMine, newTool);
				breakWeakBlocks(blockPositionToMine);
			} else if (this.getDamage() > KGConfig.mineGunThirdLevel.get()) {
				newTool = new ItemStack(Items.STONE_PICKAXE);
				tryBreakBlock(blockPositionToMine, newTool);
				newTool = new ItemStack(Items.STONE_AXE);
				tryBreakBlock(blockPositionToMine, newTool);
				newTool = new ItemStack(Items.STONE_SHOVEL);
				tryBreakBlock(blockPositionToMine, newTool);
				breakWeakBlocks(blockPositionToMine);
			} else if (this.getDamage() > KGConfig.mineGunSecondLevel.get()) {
				newTool = new ItemStack(Items.WOODEN_PICKAXE);
				tryBreakBlock(blockPositionToMine, newTool);
				newTool = new ItemStack(Items.WOODEN_AXE);
				tryBreakBlock(blockPositionToMine, newTool);
				newTool = new ItemStack(Items.WOODEN_SHOVEL);
				tryBreakBlock(blockPositionToMine, newTool);
				breakWeakBlocks(blockPositionToMine);
			} else {
				breakWeakBlocks(blockPositionToMine);
			}

			//don't do corruption stuff if the block wasn't successfully mined
			if ((isCorrupted) && (level.getBlockState(blockPositionToMine).getBlock() == Blocks.AIR)) {
				level.setBlock(blockPositionToMine, Blocks.NETHERRACK.defaultBlockState(), 1);
				//don't place fire if something is above current block
				if (isOnFire() &&
						(random.nextDouble() < KGConfig.netheriteMinegunIgnitionChance.get()) &&
						(level.getBlockState(blockPositionToMine.above(1)).getBlock() == Blocks.AIR)) {
					level.setBlock(blockPositionToMine.above(1), Blocks.FIRE.defaultBlockState(), 3);
				}
			}
		}
		if (!level.isClientSide && this.shouldCollateral) remove();
	}

	protected void breakWeakBlocks(BlockPos blockPosToTest) {
		if (!level.getBlockState(blockPosToTest).requiresCorrectToolForDrops()) {
			Random random = new Random();
			if (KGConfig.diamondMinegunMineChance.get() - random.nextDouble() > 0)
				this.level.destroyBlock(blockPosToTest, true);
		}
	}

	protected void entityHitProcess(Entity entity) {
		Entity shooter = getOwner();
		IBullet bullet = (IBullet) getItemRaw().getItem();

		//get health of the victim before they get hit.
		if (entity instanceof LivingEntity) {
			LivingEntity victim = (LivingEntity) entity;
			if (shouldGlow) {
				victim.addEffect(new EffectInstance(Effects.GLOWING, 120, 1));
			}
			healthOfVictim = victim.getHealth();
		} else healthOfVictim = 0.0f;

		if (isOnFire()) entity.setSecondsOnFire(5);
		int lastHurtResistant = entity.invulnerableTime;
		if (ignoreInvulnerability) entity.invulnerableTime = 0;

		Vector3d previousDelta = entity.getDeltaMovement();
		boolean damaged = entity.hurt((new IndirectEntityDamageSource("arrow", this, shooter)).setProjectile(), (float) bullet.modifyDamage(damage, this, entity, shooter, level));
		if (isClean) entity.setDeltaMovement(previousDelta);

		if (damaged && entity instanceof LivingEntity) {
			LivingEntity livingTarget = (LivingEntity) entity;

			double actualKnockback;
			if (this.shootingGun.getItem() == ModItems.doubleBarrelShotgun) {
				actualKnockback = knockbackStrength / ticksSinceFired;

				Vector3d vec;
				vec = getDeltaMovement().multiply(1, 0.25, 1).normalize().scale(actualKnockback);
				livingTarget.push(vec.x, vec.y, vec.z);
			}

			bullet.onLivingEntityHit(this, livingTarget, shooter, level);
		} else if (!damaged && ignoreInvulnerability) entity.invulnerableTime = lastHurtResistant;
	}

	@Override
	protected void onHit(RayTraceResult result) {
		//explode or damage?
		if (isExplosive && !level.isClientSide) {
			EntityRayTraceResult victim = (EntityRayTraceResult) result.hitInfo;
			float newRadius = (float) (double) KGConfig.diamondLauncherDamageMultiplier.get();

			//if projectile is stronger than flint damage assume a stronger material type
			if (getDamage() > KGConfig.flintBulletDamage.get() * KGConfig.diamondLauncherDamageMultiplier.get()) newRadius += KGConfig.explosionIncreaseOnStrongerTier.get();

			level.explode(this, result.getLocation().x, result.getLocation().y, result.getLocation().z, newRadius, isOnFire(), KGConfig.explosionsEnabled.get() ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
			if (isWither) {
				newRadius *= KGConfig.witherLauncherEffectRadiusMultiplier.get();
				AxisAlignedBB witherTrace = new AxisAlignedBB(result.getLocation().x - newRadius, result.getLocation().y - newRadius, result.getLocation().z - newRadius, result.getLocation().x + newRadius, result.getLocation().y + newRadius, result.getLocation().z + newRadius);
				List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, witherTrace);

				for (LivingEntity mob : entities) {
					mob.addEffect(new EffectInstance(Effects.WITHER, 200, 1));
				}
			}
		}
		//damage should try to hurt tiles and entities without using an explosion, so it will need to fire this super.
		else super.onHit(result);

		//remove will be present inside onHitBlock instead
		if (!level.isClientSide && !shouldCollateral) {
			remove();
		}
	}

	protected void tryBreakBlock(BlockPos blockPosToTest, ItemStack stack) {
		//test if the tool tier found works

		if (ForgeHooks.isToolEffective(this.level, blockPosToTest, stack)) {
			//drop the block in a fixed chance
			Random random = new Random();
			if (KGConfig.diamondMinegunMineChance.get() - random.nextDouble() > 0)
				this.level.destroyBlock(blockPosToTest, true);
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("tsf", ticksSinceFired);
		compound.putDouble("damage", damage);
		compound.putBoolean("explosive", isExplosive);
		compound.putBoolean("collateral", shouldCollateral);
		//compound.putBoolean("isPlasma", isPlasma);
		if (ignoreInvulnerability) compound.putBoolean("ignoreinv", ignoreInvulnerability);
		if (knockbackStrength != 0) compound.putDouble("knockback", knockbackStrength);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		ticksSinceFired = compound.getInt("tsf");
		damage = compound.getDouble("damage");
		isExplosive = compound.getBoolean("explosive");
		shouldCollateral = compound.getBoolean("collateral");
		//isPlasma = compound.getBoolean("isPlasma");
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

	public double getInaccuracy() {
		return inaccuracy;
	}

	public void setHealthRewardChance(double rewardChance) {
		this.healthRewardChance = rewardChance;
	}

	;

	public float getHealthOfVictim() {
		return healthOfVictim;
	}

	;

	public void setShouldBreakBlock(boolean breakBlock) {
		this.shouldBreakBlock = breakBlock;
	}

	;

	public boolean rollRewardChance() {
		Random random = new Random();
		return (healthRewardChance - random.nextDouble()) > 0.0D;
	}

	public void setInaccuracy(double inaccuracy) {
		this.inaccuracy = inaccuracy;
	}

	public void setBulletSpeed(double speed) {
		this.bulletSpeed = speed;
	}

	public void setIgnoreInvulnerability(boolean ignoreInvulnerability) {
		//quick workaround, always make it ignore invulnerability.
		this.ignoreInvulnerability = ignoreInvulnerability;
	}

	public void setShouldCollateral(boolean collateral) {
		this.shouldCollateral = collateral;
	}

	public void setShouldGlow(boolean glow) {
		this.shouldGlow = glow;
	}

	public void setIsCritical(boolean critical) {
		this.isCritical = critical;
	}

	public boolean isCritical() {
		return this.isCritical;
	}

	public void setShootingGun(GunItem gun) {
		this.shootingGun = gun;
	}

	public void setExplosive(boolean explosive) {
		this.isExplosive = explosive;
	}

	public GunItem getShootingGun() {
		return this.shootingGun;
	}

	public void setOrigin(Vector3d shooterOrigin) { this.origin = shooterOrigin; }

	public Vector3d getOrigin() { return this.origin; }

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
		return NetworkUtils.getProjectileSpawnPacket(this);
	}
}
