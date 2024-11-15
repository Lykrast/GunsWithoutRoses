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
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.phys.Vec3;
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
	protected double headshotMult = 1;
	protected int projectiles = 1;
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
		ItemStack ammo = player.getProjectile(gun);

		if (!ammo.isEmpty() || player.getAbilities().instabuild) {
			if (!world.isClientSide) {
				//this is for creative
				if (ammo.isEmpty()) ammo = new ItemStack(GWRItems.ironBullet.get());
				//There was at least one instance of quiver mod not respecting getAmmoPredicate()
				//so I have to put wayyy more instanceof IBullet checks than I should need to >:(
				IBullet parentBullet = (IBullet) (ammo.getItem() instanceof IBullet ? ammo.getItem() : GWRItems.ironBullet.get());
				//For the bullet bag we doing the indirection here
				ItemStack firedAmmo = ammo;
				IBullet firedBullet = parentBullet;
				if (parentBullet.hasDelegate(ammo, player)) {
					firedAmmo = parentBullet.getDelegate(ammo, player);
					firedBullet = (IBullet) (firedAmmo.getItem() instanceof IBullet ? firedAmmo.getItem() : GWRItems.ironBullet.get());
				}

				boolean bulletFree = player.getAbilities().instabuild || !shouldConsumeAmmo(gun, player);

				//Workaround for quivers not respecting getAmmoPredicate()
				if (!(firedAmmo.getItem() instanceof IBullet)) firedAmmo = new ItemStack(GWRItems.ironBullet.get());
				shoot(world, player, gun, firedAmmo, firedBullet, bulletFree);

				gun.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
				if (!bulletFree) parentBullet.consume(ammo, player);
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
		ItemStack override = overrideFiredStack(player, gun, ammo, bulletItem, bulletFree);
		if (override != ammo) {
			ammo = override;
			bulletItem = (IBullet) override.getItem();
		}
		int shots = getProjectilesPerShot(gun, player);
		for (int i = 0; i < shots; i++) {
			BulletEntity shot = bulletItem.createProjectile(world, ammo, player);
			shot.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, (float) getProjectileSpeed(gun, player), (float) getInaccuracy(gun, player));
			shot.setDamage(Math.max(0, shot.getDamage() + getBonusDamage(gun, player)) * getDamageMultiplier(gun, player));
			if (player.getAttribute(GWRAttributes.knockback.get()) != null) shot.setKnockbackStrength(shot.getKnockbackStrength() + player.getAttributeValue(GWRAttributes.knockback.get()));
			shot.setHeadshotMultiplier(getHeadshotMultiplier(gun, player));
			affectBulletEntity(player, gun, shot, bulletFree);

			world.addFreshEntity(shot);
		}
	}

	/**
	 * This one is meant for mobs.
	 * @param mobSpread added spread to the shot that scales with difficulty (vanilla skeletons have x10/6/2 on easy/medium/hard), but will keep shotgun spreads the same
	 */
	public void shootAt(LivingEntity shooter, LivingEntity target, ItemStack gun, ItemStack ammo, IBullet bulletItem, double mobSpread, boolean bulletFree) {
		ItemStack override = overrideFiredStack(shooter, gun, ammo, bulletItem, bulletFree);
		if (override != ammo) {
			ammo = override;
			bulletItem = (IBullet) override.getItem();
		}
		Vec3 mobSpreaded = addSpread(target.getX() - shooter.getX(), target.getEyeY() - shooter.getEyeY(), target.getZ() - shooter.getZ(), mobSpread, shooter.getRandom());
		int shots = getProjectilesPerShot(gun, shooter);
		for (int i = 0; i < shots; i++) {
			BulletEntity shot = bulletItem.createProjectile(shooter.level(), ammo, shooter);
			shot.shoot(mobSpreaded.x, mobSpreaded.y, mobSpreaded.z, (float) getProjectileSpeed(gun, shooter), (float) getInaccuracy(gun, shooter));
			shot.setDamage(Math.max(0, shot.getDamage() + getBonusDamage(gun, shooter)) * getDamageMultiplier(gun, shooter));
			if (shooter.getAttribute(GWRAttributes.knockback.get()) != null) shot.setKnockbackStrength(shot.getKnockbackStrength() + shooter.getAttributeValue(GWRAttributes.knockback.get()));
			shot.setHeadshotMultiplier(getHeadshotMultiplier(gun, shooter));
			affectBulletEntity(shooter, gun, shot, bulletFree);

			shooter.level().addFreshEntity(shot);
		}
	}

	/**
	 * Adds spread like the vanilla projectile shoot method (but without scaling for speed).
	 * So that shotgun spreads remain unaffected by mobs shooting inaccurately.
	 */
	public static Vec3 addSpread(double x, double y, double z, double spread, RandomSource random) {
		return (new Vec3(x, y, z)).normalize().add(random.triangle(0, 0.0172275 * spread), random.triangle(0, 0.0172275 * spread), random.triangle(0, 0.0172275 * spread));
	}

	public SoundEvent getFireSound() {
		return fireSound.get();
	}
	
	/**
	 * Lets you completely replace the actual bullet being fired, which will only affect the projectile (the original ammo is still consumed).
	 * <br/>For example to do something like Terrarria's Venus Magnum that replaces Musket Balls/Tungsten Bullets with High Velocity Bullets.
	 * <br/>If the fired stack differs from the original (by !=) then its item will be cast to an IBullet.
	 * <br/>That's kind of a mess, but just doing a "if ammo.getItem.is(tag base bullet) return new ItemStack(blazing bullet)" works so is good for me.
	 */
	protected ItemStack overrideFiredStack(LivingEntity shooter, ItemStack gun, ItemStack ammo, IBullet bulletItem, boolean bulletFree) {
		return ammo;
	}

	/**
	 * Lets the gun do custom stuff to default bullets without redoing all the base stuff from shoot.
	 */
	protected void affectBulletEntity(LivingEntity shooter, ItemStack gun, BulletEntity bullet, boolean bulletFree) {

	}

	/**
	 * Rolls chance to know if ammo should be consumed for the shot. Applies both the baseline chance and Preserving enchantment.<br>
	 * If you change this don't forget to tweak getInverseChanceFreeShot accordingly for the tooltip (and call super).
	 */
	public boolean shouldConsumeAmmo(ItemStack stack, LivingEntity shooter) {
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
		if (shooter != null && shooter.getAttribute(GWRAttributes.fireDelay.get()) != null) delay = (int) (delay * shooter.getAttributeValue(GWRAttributes.fireDelay.get()));
		return Math.max(1, delay);
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
	 * Whether the gun can land sniper crits, which only happens if the base headshot mult is more than 1.
	 */
	public boolean canHeadshot() {
		return headshotMult > 1;
	}
	
	public double getHeadshotMultiplier(ItemStack stack, @Nullable LivingEntity shooter) {
		if (!canHeadshot()) return 1;
		double mult = headshotMult;
		//Deadeye
		int deadeye = stack.getEnchantmentLevel(GWREnchantments.deadeye.get());
		if (deadeye >= 1) mult = GWREnchantments.deadeyeModify(deadeye, mult);
		//attribute
		if (shooter != null && shooter.getAttribute(GWRAttributes.sniperMult.get()) != null) mult += shooter.getAttributeValue(GWRAttributes.sniperMult.get());
		//attribute can lower it, so we cap it at 1 (where it disables headshots)
		return Math.max(1, mult);
	}
	
	/**
	 * Whether the gun has multiple projectiles per shot, which only happens if it's more than 1 on baseline.
	 */
	public boolean hasMultipleProjectiles() {
		return projectiles > 1;
	}
	
	public int getProjectilesPerShot(ItemStack stack, @Nullable LivingEntity shooter) {
		if (!hasMultipleProjectiles()) return 1;
		//no enchantments but we got attributes and it might lower the proj count
		//still want to shoot at least 1 bullet tho
		if (shooter != null && shooter.getAttribute(GWRAttributes.shotgunProjectiles.get()) != null) {
			return Math.max(1,(int) (projectiles + shooter.getAttributeValue(GWRAttributes.shotgunProjectiles.get())));
		}
		else return projectiles;
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
	 * Says if the crit multiplier is changed from base value. Used for tooltip.
	 */
	protected boolean isCritMultiplierModified(ItemStack stack) {
		return stack.getEnchantmentLevel(GWREnchantments.deadeye.get()) >= 1;
	}

	/**
	 * Says if the projectile count is changed from base value. Used for tooltip.
	 */
	protected boolean isProjectileCountModified(ItemStack stack) {
		//no enchantments for it cause +1 projectiles is too much, but can't be fractional
		return false;
	}

	/**
	 * Sets a chance to NOT consume ammo, used when making the item for registering.
	 */
	public GunItem chanceFreeShot(double chanceFreeShot) {
		this.chanceFreeShot = chanceFreeShot;
		return this;
	}

	/**
	 * Sets a headshot multiplier. Values 1 or below means no headshot. Used when making the item for registering.
	 */
	public GunItem headshotMult(double headshotMult) {
		this.headshotMult = headshotMult;
		return this;
	}
	
	/**
	 * Sets firing multiple projectiles per shot. Values 1 or below means no shotgunning. Used when making the item for registering.
	 */
	public GunItem projectiles(int projectiles) {
		this.projectiles = projectiles;
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
		//Disallow Deadeye if the gun can't headshot
		if (enchantment == GWREnchantments.deadeye.get() && !canHeadshot()) return false;
		return super.canApplyAtEnchantingTable(stack, enchantment);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			MutableComponent values;
			//Damage
			double damageMultiplier = getDamageMultiplier(stack, null);
			double damageBonus = getBonusDamage(stack, null) * damageMultiplier;

			if (damageMultiplier != 1 || damageBonus != 0) {
				if (damageMultiplier == 1) values = Component.translatable("tooltip.gunswithoutroses.gun.damage.flat", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageBonus));
				else if (damageBonus == 0) values = Component.translatable("tooltip.gunswithoutroses.gun.damage.mult", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageMultiplier));
				else values = Component.translatable("tooltip.gunswithoutroses.gun.damage.both", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageMultiplier), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageBonus));
				
				tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.damage", values.withStyle(ChatFormatting.WHITE)).withStyle(isDamageModified(stack) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.DARK_GREEN));
			}
			
			if (canHeadshot()) {
				double headshotMultiplier = getHeadshotMultiplier(stack, null);
				//get the formatted total damage
				double hsDamageMultiplier = damageMultiplier*headshotMultiplier;
				double hsDamageBonus = damageBonus*headshotMultiplier;
				if (damageBonus == 0) values = Component.translatable("tooltip.gunswithoutroses.gun.damage.mult", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(hsDamageMultiplier));
				else values = Component.translatable("tooltip.gunswithoutroses.gun.damage.both", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(hsDamageMultiplier), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(hsDamageBonus));
				//get that on the values component
				values = Component.translatable("tooltip.gunswithoutroses.sniper.headshot.values",
						Component.literal(ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(headshotMultiplier)).withStyle(ChatFormatting.WHITE),
						values.withStyle(ChatFormatting.WHITE))
						.withStyle(ChatFormatting.GRAY);
				
				tooltip.add(Component.translatable("tooltip.gunswithoutroses.sniper.headshot", values).withStyle(isCritMultiplierModified(stack) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.DARK_GREEN));
			}
			
			if (hasMultipleProjectiles()) {
				int projectiles = getProjectilesPerShot(stack, null);
				//get the formatted total damage
				double sgDamageMultiplier = damageMultiplier*projectiles;
				double sgDamageBonus = damageBonus*projectiles;
				if (damageBonus == 0) values = Component.translatable("tooltip.gunswithoutroses.gun.damage.mult", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(sgDamageMultiplier));
				else values = Component.translatable("tooltip.gunswithoutroses.gun.damage.both", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(sgDamageMultiplier), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(sgDamageBonus));
				//get that on the values component
				if (!canHeadshot()) {
					values = Component.translatable("tooltip.gunswithoutroses.shotgun.projectiles.values",
							Component.literal(Integer.toString(projectiles)).withStyle(ChatFormatting.WHITE),
							values.withStyle(ChatFormatting.WHITE))
							.withStyle(ChatFormatting.GRAY);
				}
				else {
					//I'm not making shotguns that can headshot, but someone might
					//so this is an ugly patch up to get that edge case to still display full damage
					double headshotMultiplier = getHeadshotMultiplier(stack, null);
					MutableComponent values2;
					if (damageBonus == 0) values2 = Component.translatable("tooltip.gunswithoutroses.gun.damage.mult", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(sgDamageMultiplier*headshotMultiplier));
					else values2 = Component.translatable("tooltip.gunswithoutroses.gun.damage.both", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(sgDamageMultiplier*headshotMultiplier), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(sgDamageBonus*headshotMultiplier));
					values = Component.translatable("tooltip.gunswithoutroses.shotgun.projectiles.headshot",
							Component.literal(Integer.toString(projectiles)).withStyle(ChatFormatting.WHITE),
							values.withStyle(ChatFormatting.WHITE),
							values2.withStyle(ChatFormatting.WHITE))
							.withStyle(ChatFormatting.GRAY);
				}

				tooltip.add(Component.translatable("tooltip.gunswithoutroses.shotgun.projectiles", values).withStyle(isProjectileCountModified(stack) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.DARK_GREEN));
			}

			//Fire rate
			int fireDelay = getFireDelay(stack, null);
			values = Component.translatable("tooltip.gunswithoutroses.gun.firerate.values", 
					Component.literal(Integer.toString(fireDelay)).withStyle(ChatFormatting.WHITE),
					Component.literal(Integer.toString((60 * 20) / fireDelay)).withStyle(ChatFormatting.WHITE))
					.withStyle(ChatFormatting.GRAY);
			tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.firerate", values).withStyle(isFireDelayModified(stack) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.DARK_GREEN));

			//Accuracy
			if (hasPerfectAccuracy()) values = Component.translatable("tooltip.gunswithoutroses.gun.accuracy.perfect").withStyle(ChatFormatting.WHITE);
			else values = Component.literal(ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(getInaccuracy(stack, null))).withStyle(ChatFormatting.WHITE);
			tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.accuracy", values).withStyle(isInaccuracyModified(stack) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.DARK_GREEN));

			//Chance to not consume ammo
			double inverseChanceFree = getInverseChanceFreeShot(stack);
			if (inverseChanceFree < 1) {
				values = Component.translatable("tooltip.gunswithoutroses.gun.chance_free.values", (int) ((1 - inverseChanceFree) * 100)).withStyle(ChatFormatting.WHITE);
				tooltip.add(Component.translatable("tooltip.gunswithoutroses.gun.chance_free", values).withStyle(isChanceFreeShotModified(stack) ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.DARK_GREEN));
			}
			
			//Headshot notification
			if (canHeadshot()) tooltip.add(Component.translatable("tooltip.gunswithoutroses.sniper.headshot.help").withStyle(ChatFormatting.GRAY));

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
	public static final Predicate<ItemStack> BULLETS = (stack) -> stack.getItem() instanceof IBullet && ((IBullet) stack.getItem()).hasAmmo(stack);

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
