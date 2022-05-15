package xyz.kaleidiodev.kaleidiosguns.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.eventbus.api.Event;
import xyz.kaleidiodev.kaleidiosguns.item.IBullet;
import xyz.kaleidiodev.kaleidiosguns.network.NetworkUtils;
import xyz.kaleidiodev.kaleidiosguns.registry.ModEntities;
import xyz.kaleidiodev.kaleidiosguns.registry.ModSounds;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BulletEntity extends AbstractFireballEntity {

	protected double damage = 1;
	protected double inaccuracy = 0.0;
	protected boolean ignoreInvulnerability = false;
	protected double knockbackStrength = 0;
	protected int ticksSinceFired;
	protected double healthRewardChance = 0.0f;
	protected float healthOfVictim;
	protected boolean shouldBreakBlock;
	protected boolean shouldCollateral;
	protected double bulletSpeed;

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

	@Override
	public void tick() {
		//Using a thing I save so that bullets don't get clogged up on chunk borders
		ticksSinceFired++;
		if (ticksSinceFired > 100) {
			remove();
		}

		super.tick();
	}

	@Override
	protected void onHitEntity(EntityRayTraceResult raytrace) {
		super.onHitEntity(raytrace); //this seems to be on the right track, but we also need a manual raytrace to get a full list of entities in the next delta, just in case the projectile is moving too fast

		if (!level.isClientSide) {
			Entity target = raytrace.getEntity();
			entityHitProcess(target);
		}
	}

	protected void entityHitProcess(Entity entity) {
		Entity shooter = getOwner();
		IBullet bullet = (IBullet) getItemRaw().getItem();

		//get health of the victim before they get hit.
		LivingEntity victim = (LivingEntity)entity;
		healthOfVictim = victim.getHealth();

		if (isOnFire()) entity.setSecondsOnFire(5);
		int lastHurtResistant = entity.invulnerableTime;
		if (ignoreInvulnerability) entity.invulnerableTime = 0;
		boolean damaged = entity.hurt((new IndirectEntityDamageSource("arrow", this, shooter)).setProjectile(), (float) bullet.modifyDamage(damage, this, entity, shooter, level));

		if (damaged && entity instanceof LivingEntity) {
			LivingEntity livingTarget = (LivingEntity)entity;

			if (knockbackStrength > 0) {
				double actualKnockback = knockbackStrength;
				Vector3d vec = getDeltaMovement().multiply(1, 0, 1).normalize().scale(actualKnockback);
				if (vec.lengthSqr() > 0) livingTarget.push(vec.x, 0.1, vec.z);
			}

			if (shooter instanceof LivingEntity) doEnchantDamageEffects((LivingEntity)shooter, entity);

			bullet.onLivingEntityHit(this, livingTarget, shooter, level);
		}
		else if (!damaged && ignoreInvulnerability) entity.invulnerableTime = lastHurtResistant;
	}

	@Override
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		if (!level.isClientSide) {
			//play a sound when it lands on a block
			if (result.getType() == RayTraceResult.Type.BLOCK) {
				this.level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.impact, this.getSoundSource(), 0.25f, 1.0f);

				if (shouldBreakBlock) {
					//test if the block is of the right tool type to mine with.
					//we could not guarantee the projectile ended up inside the block on this tick, so let's add some mathematics to work around that
					BlockRayTraceResult blockResult = (BlockRayTraceResult) result;

					BlockPos blockPositionToMine = ((BlockRayTraceResult) result).getBlockPos();
					ItemStack newTool;

					if (this.getDamage() > 9.0D) {
						newTool = new ItemStack(Items.DIAMOND_PICKAXE);
						tryBreakBlock(blockPositionToMine, newTool);
						newTool = new ItemStack(Items.DIAMOND_AXE);
						tryBreakBlock(blockPositionToMine, newTool);
						newTool = new ItemStack(Items.DIAMOND_SHOVEL);
						tryBreakBlock(blockPositionToMine, newTool);
					} else if (this.getDamage() > 6.0D) {
						newTool = new ItemStack(Items.IRON_PICKAXE);
						tryBreakBlock(blockPositionToMine, newTool);
						newTool = new ItemStack(Items.IRON_AXE);
						tryBreakBlock(blockPositionToMine, newTool);
						newTool = new ItemStack(Items.IRON_SHOVEL);
						tryBreakBlock(blockPositionToMine, newTool);
					} else if (this.getDamage() > 5.0D) {
						newTool = new ItemStack(Items.STONE_PICKAXE);
						tryBreakBlock(blockPositionToMine, newTool);
						newTool = new ItemStack(Items.STONE_AXE);
						tryBreakBlock(blockPositionToMine, newTool);
						newTool = new ItemStack(Items.STONE_SHOVEL);
						tryBreakBlock(blockPositionToMine, newTool);
					} else {
						newTool = new ItemStack(Items.WOODEN_PICKAXE);
						tryBreakBlock(blockPositionToMine, newTool);
						newTool = new ItemStack(Items.WOODEN_AXE);
						tryBreakBlock(blockPositionToMine, newTool);
						newTool = new ItemStack(Items.WOODEN_SHOVEL);
						tryBreakBlock(blockPositionToMine, newTool);
					}
				}
				remove();
			}
			if (shouldCollateral){
				//put some code here for the manual raytrace
				//the raytrace needs to be from current position to delta from last known position
				List<Entity> entities = new ArrayList<Entity>();
				AxisAlignedBB bb = this.getBoundingBox();
				Vector3d incPosition = new Vector3d(this.getDeltaMovement().x / (bulletSpeed * 10),this.getDeltaMovement().y / (bulletSpeed * 10),this.getDeltaMovement().z / (bulletSpeed * 10));

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
				}

				//because the sniper cannot have a projectile ignore invulnerability anyway, this is safe to do.
				for (Entity entity : entities) {
					if (!(entity instanceof PlayerEntity) && (entity instanceof LivingEntity)) entityHitProcess(entity);
				}

			}
			else remove();
		}
	}

	protected void tryBreakBlock(BlockPos blockPosToTest, ItemStack stack) {
		//test if the tool tier found works

		if (ForgeHooks.isToolEffective(this.level, blockPosToTest, stack))
		{
			//drop the block in a fixed chance
			Random random = new Random();
			if (0.2D - random.nextDouble() > 0) this.level.destroyBlock(blockPosToTest, true);
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("tsf", ticksSinceFired);
		compound.putDouble("damage", damage);
		//compound.putBoolean("isPlasma", isPlasma);
		if (ignoreInvulnerability) compound.putBoolean("ignoreinv", ignoreInvulnerability);
		if (knockbackStrength != 0) compound.putDouble("knockback", knockbackStrength);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		ticksSinceFired = compound.getInt("tsf");
		damage = compound.getDouble("damage");
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

	public void setHealthRewardChance(double rewardChance) { this.healthRewardChance = rewardChance; };

	public float getHealthOfVictim() { return healthOfVictim; };

	public void setShouldBreakBlock(boolean breakBlock) { this.shouldBreakBlock = breakBlock; };

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
