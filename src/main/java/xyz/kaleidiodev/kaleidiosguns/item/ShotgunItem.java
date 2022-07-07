package xyz.kaleidiodev.kaleidiosguns.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import xyz.kaleidiodev.kaleidiosguns.config.KGConfig;
import xyz.kaleidiodev.kaleidiosguns.registry.ModEnchantments;

import javax.annotation.Nullable;
import java.util.List;

public class ShotgunItem extends GunItem {

	private final int bulletCount;
	protected boolean isVampire;

	public ShotgunItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability, int bulletCount) {
		super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
		this.bulletCount = bulletCount;
	}

	@Override
	protected void fireWeapon(World world, PlayerEntity player, ItemStack gun, ItemStack ammo, IBullet bulletItem, boolean bulletFree) {
		for (int i = 0; i < getBulletCount(gun, player); i++) super.fireWeapon(world, player, gun, ammo, bulletItem, bulletFree);
	}

	@Override
	protected void addExtraStatsTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip) {
		tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.shotgun.shots" + (isProjectileCountModified(stack) ? ".modified" : ""), getBulletCount(stack, null)));
	}

	protected int getBulletCount(ItemStack stack, @Nullable PlayerEntity player) {
		int entityCount = 1;
		if (isVampire && player != null) {
			List<Entity> victims = player.level.getEntitiesOfClass(Entity.class, AxisAlignedBB.ofSize(10, 10, 10).move(player.position()));

			for (Entity mob : victims) {
				 if (mob instanceof LivingEntity) {
					 LivingEntity creature = (LivingEntity) mob;
					 //every passive creature in this 10 block box gets a heart sacrificed for a new bullet in the shotgun
					 if (!(creature instanceof MonsterEntity) && !(creature instanceof PlayerEntity)) {
						 creature.hurt((new EntityDamageSource("magic", (Entity) player)), 2); //set value for vampire via config later
						 entityCount++;
					 }
				 }
			}
		}

		System.out.println(entityCount);

		//add projectile cap from config later
		return bulletCount + Math.min(25, entityCount * (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.division, stack) * KGConfig.divisionCountIncrease.get()));
	}

	protected boolean isProjectileCountModified(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.division, stack) >= 1;
	}

	public boolean getIsVampire() { return isVampire; }

	public ShotgunItem setIsVampire(boolean vampire) {
		this.isVampire = vampire;
		return this;
	}

}
