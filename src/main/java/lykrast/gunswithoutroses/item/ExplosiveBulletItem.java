package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExplosiveBulletItem extends BulletItem {

	public ExplosiveBulletItem(Properties properties, int damage) {
		super(properties, damage);
	}
	
	@Override
	public void onLivingEntityHit(BulletEntity projectile, LivingEntity target, @Nullable Entity shooter, Level level, boolean headshot) {
		super.onLivingEntityHit(projectile, target, shooter, level, headshot);
		//impact where we collide with the bounding box (so the explosion is closer to the actual target)
		Vec3 pos = projectile.position();
		//inflate 0.3 is the same as the detection collision, so it's a bit farther from the target but eeeh
		pos = target.getBoundingBox().inflate(0.3).clip(pos, pos.add(projectile.getDeltaMovement())).orElse(pos);
		explode(projectile, pos, target, shooter, level, headshot);
	}
	
	@Override
	public void onBlockHit(BulletEntity projectile, BlockHitResult hit, @Nullable Entity shooter, Level level) {
		explode(projectile, hit.getLocation(), null, shooter, level, false);
	}
	
	private static final float DMG_MIN = 0.25f, DMG_MAX = 0.75f;
	
	private void explode(BulletEntity projectile, Vec3 impact, @Nullable LivingEntity target, @Nullable Entity shooter, Level level, boolean headshot) {
		double radius = Mth.sqrt((float) projectile.getDamage());
		float damage = (float) projectile.getDamage();
		if (headshot) {
			radius *= 1.5;
			damage *= projectile.getHeadshotMultiplier();
		}
		double radiusSqr = radius*radius;
		for (LivingEntity ent : level.getEntitiesOfClass(LivingEntity.class, projectile.getBoundingBox().inflate(radius+1))) {
			//don't hit the initial target twice
			if (ent.isAlive() && !ent.isInvulnerable() && ent != target) {
				double distSqr = ent.getBoundingBox().distanceToSqr(impact);
				if (distSqr < radiusSqr) {
					//75% damage when within 1 block, then decreases quadratically over distance down to 25%
					float falloff = DMG_MAX;
					if (distSqr > 1) falloff -= (DMG_MAX-DMG_MIN)*(distSqr-1)/(radiusSqr-1);
					if (projectile.isOnFire()) target.setSecondsOnFire(5);
					int lastHurtResistant = ent.invulnerableTime;
					ent.invulnerableTime = 0;
					//explosion damage!
					boolean damaged = ent.hurt(projectile.damageSources().explosion(projectile, shooter), damage*falloff);
					
					if (damaged) {
						System.out.println(damage*falloff);
						double kb = projectile.getKnockbackStrength();
						if (kb > 0) {
							Vec3 vec = ent.position().subtract(impact).multiply(1, 0, 1).normalize().scale(kb*0.6*falloff);
							if (vec.lengthSqr() > 0) ent.push(vec.x, 0.1, vec.z);
						}

						if (shooter instanceof LivingEntity) projectile.doEnchantDamageEffects((LivingEntity)shooter, ent);
					}
					else if (!damaged) ent.invulnerableTime = lastHurtResistant;
				}
			}
		}
		//visuals
		projectile.playSound(SoundEvents.GENERIC_EXPLODE);
		//this is only called on server so do this for particles
		//each of those in vanilla is like 1 block radius so we reduce their random range
		//and also like mostly eyeballing on that
		double particuleRange = Math.max(0.1, radius-2);
		((ServerLevel)level).sendParticles(ParticleTypes.EXPLOSION, impact.x, impact.y, impact.z, Math.max(1, (int)(radiusSqr*(radius-1))), particuleRange, particuleRange, particuleRange, 0);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.explosive_bullet", (int)(100*DMG_MAX), (int)(100*DMG_MIN)).withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.explosive_bullet.crit").withStyle(ChatFormatting.GRAY));
	}

}
