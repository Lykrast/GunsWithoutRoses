package lykrast.gunswithoutroses.item;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.entity.BulletEntity;
import lykrast.gunswithoutroses.registry.GWRAttributes;
import lykrast.gunswithoutroses.registry.GWREnchantments;
import lykrast.gunswithoutroses.registry.GWRItems;
import lykrast.gunswithoutroses.registry.GWRSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GunItem extends ProjectileWeaponItem {
	protected int bonusDamage;
	protected double damageMultiplier;
	protected int fireDelay;
	protected double inaccuracy;
	protected double projectileSpeed = 3;
	private int enchantability;
	protected double chanceFreeShot = 0;
	protected Supplier<SoundEvent> fireSound = GWRSounds.gun::get;
	//Hey guess what if I just put the repair material it crashes... so well let's do like vanilla and just use a supplier
	protected Supplier<Ingredient> repairMaterial;

	public GunItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability) {
		super(properties);
		this.bonusDamage = bonusDamage;
		this.damageMultiplier = damageMultiplier;
		this.enchantability = enchantability;
		this.fireDelay = fireDelay;
		this.inaccuracy = inaccuracy;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack gun = player.getItemInHand(hand);
		//"Oh yeah I will use the vanilla method so that quivers can do their thing"
		//guess what the quivers suck
		ItemStack ammo = player.getProjectile(gun);

		if (!ammo.isEmpty() || player.getAbilities().instabuild) {
			if (ammo.isEmpty()) ammo = new ItemStack(GWRItems.flintBullet.get());

			IBullet bulletItem = (IBullet) (ammo.getItem() instanceof IBullet ? ammo.getItem() : GWRItems.flintBullet.get());
			if (!world.isClientSide) {
				boolean bulletFree = player.getAbilities().instabuild || !shouldConsumeAmmo(world, gun, player);
				
				//Workaround for quivers not respecting getAmmoPredicate()
				ItemStack shotAmmo = ammo.getItem() instanceof IBullet ? ammo : new ItemStack(GWRItems.flintBullet.get());
				shoot(world, player, gun, shotAmmo, bulletItem, bulletFree);
				
				gun.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
				if (!bulletFree) bulletItem.consume(ammo, player);
			}

			world.playSound(null, player.getX(), player.getY(), player.getZ(), getFireSound(), SoundSource.PLAYERS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
			player.awardStat(Stats.ITEM_USED.get(this));

			player.getCooldowns().addCooldown(this, getFireDelay(gun, player));
			return InteractionResultHolder.consume(gun);
		}
		else return InteractionResultHolder.fail(gun);
	}
	
	/**
	 * Fires one shot after all the checks have passed. You can actually fire whatever you want here.<br>
	 * Ammo is consumed afterwards, we're only shooting the bullet(s) here.
	 * @param world world
	 * @param player Player that shoots
	 * @param gun Gun shooting
	 * @param ammo Ammo being shot
	 * @param bulletItem IBullet used for the shot, may not match the ammo
	 * @param bulletFree true if no ammo was actually consumed (creative or Preserving enchant for example)
	 */
	protected void shoot(Level world, Player player, ItemStack gun, ItemStack ammo, IBullet bulletItem, boolean bulletFree) {
		BulletEntity shot = bulletItem.createProjectile(world, ammo, player);
		shot.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, (float)getProjectileSpeed(gun, player), (float)getInaccuracy(gun, player));
		shot.setDamage((shot.getDamage() + getBonusDamage(gun, player)) * getDamageMultiplier(gun, player));
		if (player.getAttribute(GWRAttributes.knockback.get()) != null) shot.setKnockbackStrength(shot.getKnockbackStrength() + player.getAttributeValue(GWRAttributes.knockback.get()));
		changeBullet(world, player, gun, shot, bulletFree);

		world.addFreshEntity(shot);
	}
	
	/**
	 * This one is meant for mobs.
	 * @param spreadMult multiplier to spread, to adjust like difficulty (vanilla skeletons have x10/6/2 on easy/medium/hard)
	 */
	public void shootAt(LivingEntity shooter, LivingEntity target, ItemStack gun, ItemStack ammo, IBullet bulletItem, double spreadMult, boolean bulletFree) {
		BulletEntity shot = bulletItem.createProjectile(shooter.level(), ammo, shooter);
		double x = target.getX() - shooter.getX();
		double y = target.getEyeY() - shot.getY();
		double z = target.getZ() - shooter.getZ();
		shot.shoot(x, y, z, (float)getProjectileSpeed(gun, shooter), (float)(getInaccuracy(gun, shooter)*spreadMult));
		shot.setDamage((shot.getDamage() + getBonusDamage(gun, shooter)) * getDamageMultiplier(gun, shooter));
		if (shooter.getAttribute(GWRAttributes.knockback.get()) != null) shot.setKnockbackStrength(shot.getKnockbackStrength() + shooter.getAttributeValue(GWRAttributes.knockback.get()));
		changeBullet(shooter.level(), shooter, gun, shot, bulletFree);

		shooter.level().addFreshEntity(shot);
	}
	
	public SoundEvent getFireSound() {
		return fireSound.get();
	}
	
	/**
	 * Lets the gun do custom stuff to default bullets without redoing all the base stuff from shoot.
	 */
	protected void changeBullet(Level world, LivingEntity shooter, ItemStack gun, BulletEntity bullet, boolean bulletFree) {
		
	}
	
	/**
	 * Rolls chance to know if ammo should be consumed for the shot. Applies both the baseline chance and Preserving enchantment.<br>
	 * If you change this don't forget to tweak getInverseChanceFreeShot accordingly for the tooltip (and call super).
	 */
	public boolean shouldConsumeAmmo(Level world, ItemStack stack, LivingEntity shooter) {
		if (chanceFreeShot > 0 && shooter.getRandom().nextDouble() < chanceFreeShot) return false;
		if (shooter.getAttribute(GWRAttributes.chanceUseAmmo.get()) != null) {
			double chance = shooter.getAttributeValue(GWRAttributes.chanceUseAmmo.get());
			if (chance < 1 && shooter.getRandom().nextDouble() > chance) return false;
		}
		
		int preserving = stack.getEnchantmentLevel(GWREnchantments.preserving.get());
		if (preserving >= 1 && GWREnchantments.rollPreserving(preserving, shooter.getRandom())) return false;
		
		return true;
	}

	/**
	 * Gets the flat bonus damage (applied BEFORE the multiplier). This takes into account Impact enchantment.
	 */
	public double getBonusDamage(ItemStack stack, @Nullable LivingEntity shooter) {
		int impact = stack.getEnchantmentLevel(GWREnchantments.impact.get());
		double bonus = impact >= 1 ? GWREnchantments.impactBonus(impact) : 0;
		if (shooter != null && shooter.getAttribute(GWRAttributes.dmgBase.get()) != null) bonus += shooter.getAttributeValue(GWRAttributes.dmgBase.get());
		return bonusDamage + bonus;
	}
	
	public double getDamageMultiplier(ItemStack stack, @Nullable LivingEntity shooter) {
		if (shooter == null || shooter.getAttribute(GWRAttributes.dmgTotal.get()) == null) return damageMultiplier;
		else return damageMultiplier * shooter.getAttributeValue(GWRAttributes.dmgTotal.get());
	}
	
	/**
	 * Gets the min time in ticks between 2 shots. This takes into account Sleight of Hand enchantment.
	 */
	public int getFireDelay(ItemStack stack, @Nullable LivingEntity shooter) {
		int sleight = stack.getEnchantmentLevel(GWREnchantments.sleightOfHand.get());
		//Let sleight of hand round the delay first, so that the attribute affects the amount shown in the tooltip
		int delay = sleight > 0 ? GWREnchantments.sleightModify(sleight, fireDelay) : fireDelay;
		if (shooter != null && shooter.getAttribute(GWRAttributes.fireDelay.get()) != null) delay = (int)(delay * shooter.getAttributeValue(GWRAttributes.fireDelay.get()));
		return Math.max(1,delay);
	}
	
	/**
	 * Checks if the gun has baseline perfect accuracy.<br>
	 * Used for tooltip and for Bullseye (which can't be applied since it would do nothing).
	 */
	public boolean hasPerfectAccuracy() {
		return inaccuracy <= 0;
	}
	
	/**
	 * Gets the spread, taking into account Bullseye enchantment.
	 */
	public double getInaccuracy(ItemStack stack, @Nullable LivingEntity shooter) {
		//it's all doubles and multiplication here, so the order doesn't matter
		double realSpread = inaccuracy;
		if (shooter != null && shooter.getAttribute(GWRAttributes.spread.get()) != null) realSpread *= shooter.getAttributeValue(GWRAttributes.spread.get());
		int bullseye = stack.getEnchantmentLevel(GWREnchantments.bullseye.get());
		return Math.max(0, bullseye >= 1 ? GWREnchantments.bullseyeModify(bullseye, realSpread) : realSpread);
	}
	
	public double getProjectileSpeed(ItemStack stack, @Nullable LivingEntity shooter) {
		//I wanted to follow kat's suggestion and make bullseye for snipers increase projectile speed
		//But high projectile speed cause weird "snapping" issues on bullets
		return projectileSpeed;
	}
	
	/**
	 * Chance to actually CONSUME ammo, to properly multiply probabilities together.<br>
	 * Tooltip then does the math to display it nicely.
	 */
	public double getInverseChanceFreeShot(ItemStack stack) {
		//No attribute checking cause this is only for the tooltip, which does not have player context
		double chance = 1 - chanceFreeShot;
		int preserving = stack.getEnchantmentLevel(GWREnchantments.preserving.get());
		if (preserving >= 1) chance *= GWREnchantments.preservingInverse(preserving);
		return chance;
	}
	
	/**
	 * Says if the damage is changed from base value. Used for tooltip.
	 */
	protected boolean isDamageModified(ItemStack stack) {
		return stack.getEnchantmentLevel(GWREnchantments.impact.get()) >= 1;
	}
	
	/**
	 * Says if the fire delay is changed from base value. Used for tooltip.
	 */
	protected boolean isFireDelayModified(ItemStack stack) {
		return stack.getEnchantmentLevel(GWREnchantments.sleightOfHand.get()) >= 1;
	}
	
	/**
	 * Says if the (in)accuracy is changed from base value. Used for tooltip.
	 */
	protected boolean isInaccuracyModified(ItemStack stack) {
		return !hasPerfectAccuracy() && stack.getEnchantmentLevel(GWREnchantments.bullseye.get()) >= 1;
	}
	
	/**
	 * Says if the chance for free shots is changed from base value. Used for tooltip.
	 */
	protected boolean isChanceFreeShotModified(ItemStack stack) {
		return stack.getEnchantmentLevel(GWREnchantments.preserving.get()) >= 1;
	}

	/**
	 * Sets a chance to NOT consume ammo, used when making the item for registering.
	 */
	public GunItem chanceFreeShot(double chanceFreeShot) {
		this.chanceFreeShot = chanceFreeShot;
		return this;
	}
	
	/**
	 * Sets the firing sound, used when making the item for registering.
	 */
	public GunItem fireSound(Supplier<SoundEvent> fireSound) {
		this.fireSound = fireSound;
		return this;
	}

	/**
	 * Sets a projectile speed, used when making the item for registering.<br>
	 * Base value is 3. High values (like 5-6) cause weird behavior so don't with the base bullets.
	 */
	public GunItem projectileSpeed(double projectileSpeed) {
		this.projectileSpeed = projectileSpeed;
		return this;
	}
	
	/**
	 * Sets the repair material, used when making the item for registering.
	 */
	public GunItem repair(Supplier<Ingredient> repairMaterial) {
		this.repairMaterial = repairMaterial;
		return this;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		//Disallow Bullseye if the gun has perfect accuracy
		if (enchantment == GWREnchantments.bullseye.get() && hasPerfectAccuracy()) return false;
		return super.canApplyAtEnchantingTable(stack, enchantment);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			//Damage
			double damageMultiplier = getDamageMultiplier(stack, null);
			double damageBonus = getBonusDamage(stack, null) * damageMultiplier;
			
			if (damageMultiplier != 1) {
				if (damageBonus != 0) tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.damage.both" + (isDamageModified(stack) ? ".modified" : ""), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageMultiplier), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageBonus)));
				else tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.damage.mult" + (isDamageModified(stack) ? ".modified" : ""), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageMultiplier)));
			}
			else if (damageBonus != 0) tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.damage.flat" + (isDamageModified(stack) ? ".modified" : ""), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageBonus)));
			
			//Fire rate
			int fireDelay = getFireDelay(stack, null);
			tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.firerate" + (isFireDelayModified(stack) ? ".modified" : ""), fireDelay, (60*20) / fireDelay));
			
			//Accuracy
			double inaccuracy = getInaccuracy(stack, null);
			if (inaccuracy <= 0) tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.accuracy.perfect" + (isInaccuracyModified(stack) ? ".modified" : "")));
			else tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.accuracy" + (isInaccuracyModified(stack) ? ".modified" : ""), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(inaccuracy)));
			
			//Chance to not consume ammo
			double inverseChanceFree = getInverseChanceFreeShot(stack);
			if (inverseChanceFree < 1) tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.chance_free" + (isChanceFreeShotModified(stack) ? ".modified" : ""), (int)((1 - inverseChanceFree) * 100)));
			
			addExtraStatsTooltip(stack, world, tooltip);
		}
		else tooltip.add(Component.translatable("tooltip.gunswithoutroses.shift"));
	}
	
	/**
	 * Add more tooltips that will be displayed below the base stats.
	 */
	protected void addExtraStatsTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip) {
		
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}

	//TODO ammo types
	private static final Predicate<ItemStack> BULLETS = (stack) -> stack.getItem() instanceof IBullet && ((IBullet)stack.getItem()).hasAmmo(stack);

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return BULLETS;
	}

	@Override
	public int getDefaultProjectileRange() {
		// No idea what this is yet, so using the Bow value (Crossbow is 8)
		return 15;
	}
	
	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return (repairMaterial != null && repairMaterial.get().test(repair)) || super.isValidRepairItem(toRepair, repair);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged || !ItemStack.isSameItem(oldStack, newStack);
	}

}
