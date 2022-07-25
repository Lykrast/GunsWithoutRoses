package xyz.kaleidiodev.kaleidiosguns.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.kaleidiodev.kaleidiosguns.config.KGConfig;
import xyz.kaleidiodev.kaleidiosguns.entity.BulletEntity;
import xyz.kaleidiodev.kaleidiosguns.registry.ModEnchantments;
import xyz.kaleidiodev.kaleidiosguns.registry.ModItems;
import xyz.kaleidiodev.kaleidiosguns.registry.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static xyz.kaleidiodev.kaleidiosguns.KaleidiosGuns.VivecraftForgeExtensionPresent;

public class GunItem extends ShootableItem {

	protected int bonusDamage;
	public double damageMultiplier;
	protected int fireDelay;
	protected double inaccuracy;
	protected double projectileSpeed = 3;
	private final int enchantability;
	protected boolean ignoreInvulnerability = true;
	protected double chanceFreeShot = 0;
	protected boolean hasBlockMineAbility = false;
	protected int revolutions = 1;
	protected int chamber = 1;
	protected boolean shouldCollateral = false;
	protected int barrelSwitchSpeed = -1;
	protected double myKnockback = 0.1D;
	protected int stabilityTime;
	protected int shotsBeforeStability;
	protected int stabilizerTimer; //internal timer.  falls to zero when gun is stable.
	protected long ticksPassed;
	protected double instabilitySpreadAdditional; //additional spread to add every time a shot recharges stabilizer timer.
	protected boolean shouldCombo;
	protected int comboCount;
	protected UUID comboVictim;
	protected boolean isExplosive;
	protected boolean lucky;
	protected boolean isWither;
	protected boolean shouldRevenge;

	protected SoundEvent fireSound = ModSounds.gun;
	protected SoundEvent reloadSound = ModSounds.double_shotgunReload;
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
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack gun = player.getItemInHand(hand);
		//"Oh yeah I will use the vanilla method so that quivers can do their thing"
		//guess what the quivers suck
		ItemStack ammo = player.getProjectile(gun);

		if (!ammo.isEmpty() || player.abilities.instabuild) {
			if (ammo.isEmpty()) ammo = new ItemStack(ModItems.flintBullet);

			IBullet bulletItem = (IBullet) (ammo.getItem() instanceof IBullet ? ammo.getItem() : ModItems.flintBullet);
			if (!world.isClientSide) {
				boolean bulletFree = player.abilities.instabuild || !shouldConsumeAmmo(gun, player);

				//Workaround for quivers not respecting getAmmoPredicate()
				ItemStack shotAmmo = ammo.getItem() instanceof IBullet ? ammo : new ItemStack(ModItems.flintBullet);
				fireWeapon(world, player, gun, shotAmmo, bulletItem, bulletFree);

				gun.hurtAndBreak(ammo.getDamageValue() > KGConfig.flintBulletDamage.get() ? 2 : 1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
				if (!bulletFree) bulletItem.consume(ammo, player);
			}

			world.playSound(null, player.getX(), player.getY(), player.getZ(), fireSound, SoundCategory.PLAYERS, 10.0F, 1.0F);
			player.awardStat(Stats.ITEM_USED.get(this));

			//change chamber if multiple revolutions
			//the method gets fired twice, once on server once on client.  let's only do it once
			if (!world.isClientSide()) {
				if (this.revolutions > 1) {
					this.chamber--;
					if (this.chamber <= 0) {
						world.playSound(null, player.getX(), player.getY(), player.getZ(), reloadSound, SoundCategory.PLAYERS, 1.0F, 1.0F);
						this.chamber = this.revolutions;
					}
				}
			}

			player.getCooldowns().addCooldown(this, getFireDelay(gun, player));
			return ActionResult.consume(gun);
		}
		else return ActionResult.fail(gun);
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
	protected void fireWeapon(World world, PlayerEntity player, ItemStack gun, ItemStack ammo, IBullet bulletItem, boolean bulletFree) {
		BulletEntity shot = bulletItem.createProjectile(world, ammo, player, gun.getItem() == ModItems.plasmaGatling);
		shot.shootFromRotation(player, player.xRot, player.yRot, 0, (float)getProjectileSpeed(gun, player), VivecraftForgeExtensionPresent ? 0.0F : (float)getInaccuracy(gun, player));

		//subtract player velocity to make the bullet independent
		Vector3d projectileMotion = player.getDeltaMovement();
		shot.setDeltaMovement(shot.getDeltaMovement().subtract(projectileMotion.x, player.isOnGround() ? 0.0D : projectileMotion.y, projectileMotion.z));

		//lucky shot should enable all lucky modifications simultaneously
		double luckyChance = KGConfig.luckyShotChance.get() * EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.luckyShot, gun);
		this.lucky = random.nextDouble() < luckyChance;

		shot.setShootingGun(this);
		shot.setInaccuracy(getInaccuracy(gun, player));
		shot.setIgnoreInvulnerability(ignoreInvulnerability);
		shot.setHealthRewardChance(EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.passionForBlood, gun) * 0.1);
		shot.setShouldBreakBlock(hasBlockMineAbility);
		shot.setShouldCollateral(shouldCollateral);
		shot.setBulletSpeed(getProjectileSpeed(gun, player));
		shot.setKnockbackStrength(myKnockback);
		shot.setExplosive(isExplosive);
		shot.setOrigin(player.position());

		double someDamage = (shot.getDamage() + getBonusDamage(gun, player)) * getDamageMultiplier(gun);
		if (gun.getItem() instanceof ShotgunItem) {
			ShotgunItem thisGun = (ShotgunItem)gun.getItem();
			shot.setDamage(someDamage * ((double)thisGun.getBaseBulletCount() / (double)thisGun.getBulletCount(gun, player)));
		}
		else shot.setDamage(someDamage);

		shot.wasRevenge = (this.shouldRevenge && (player.getHealth() < (player.getMaxHealth() * KGConfig.emeraldBlessedHealthMinimumRatio.get())));

		shot.setIsCritical(this.lucky);
		if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.marker, gun) == 1) shot.setShouldGlow(true);

		shot.noPhysics = this.shouldCollateral;
		shot.shouldCombo = this.shouldCombo;
		shot.isPlasma = (this.getItem() == ModItems.plasmaGatling);
		shot.shouldMakeFire = (ammo.getItem() == ModItems.blazeBullet);
		shot.frostyDistance = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.frostShot, gun) * KGConfig.frostyDistancePerLevel.get();
		shot.isWither = this.isWither;

		changeBullet(world, player, gun, shot, bulletFree);

		//reset timer for stability
		int base = Math.max(1, stabilityTime - (int)(stabilityTime * EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.sleightOfHand, gun) * KGConfig.sleightOfHandFireRateDecrease.get()));
		stabilizerTimer = base;
		shotsBeforeStability++;

		world.addFreshEntity(shot);
	}

	//used to tick the stability timer on guns that use it.
	@Override
	public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
		//turns out this method gets called for every chunk generator thread.  let's avoid that, with a tick elapsed counter and client filter
		long currentTime = pLevel.getGameTime();
		if (currentTime > this.ticksPassed) {
			this.ticksPassed = currentTime;
			this.onActualInventoryTick();
		}

		super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
	}

	protected void onActualInventoryTick() {
		if (this.stabilizerTimer > 0) {
			this.stabilizerTimer--;
		}

		if (this.stabilizerTimer == 0) {
			this.shotsBeforeStability = 0;
		}
	}

	/**
	 * Lets the gun do custom stuff to default bullets without redoing all the base stuff from shoot.
	 */
	protected void changeBullet(World world, PlayerEntity player, ItemStack gun, BulletEntity bullet, boolean bulletFree) {

	}

	/**
	 * Rolls chance to know if ammo should be consumed for the shot. Applies both the baseline chance and Preserving enchantment.<br>
	 * If you change this don't forget to tweak getInverseChanceFreeShot accordingly for the tooltip (and call super).
	 */
	public boolean shouldConsumeAmmo(ItemStack stack, PlayerEntity player) {
		if (chanceFreeShot > 0 && random.nextDouble() < chanceFreeShot) return false;

		int preserving = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.preserving, stack);
		if ((preserving * KGConfig.preservingRateIncrease.get())- random.nextDouble() > 0) return false;

		return true;
	}

	/**
	 * Gets the flat bonus damage (applied BEFORE the multiplier). This takes into account Impact enchantment.
	 */
	public double getBonusDamage(ItemStack stack, @Nullable PlayerEntity player) {
		int impact = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.impact, stack);
		return bonusDamage + (impact >= 1 ? (impact * KGConfig.impactDamageIncrease.get()) : 0);
	}

	public double getDamageMultiplier(ItemStack stack) {
		return damageMultiplier;
	}

	/**
	 * Gets the min time in ticks between 2 shots. This takes into account Sleight of Hand enchantment.
	 */
	public int getFireDelay(ItemStack stack, @Nullable PlayerEntity player) {
		int base = Math.max(1, fireDelay - (int)(fireDelay * EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.sleightOfHand, stack) * KGConfig.sleightOfHandFireRateDecrease.get()));
		//have instant tick time if barrel side has been switched
		if (chamber == revolutions) {
			return base;
		}
		else
		{
			return Math.max(1, base / barrelSwitchSpeed);
		}
	}

	/**
	 * Checks if the gun has baseline perfect accuracy.<br>
	 * Used for tooltip and for Bullseye (which can't be applied since it would do nothing).
	 */
	public boolean hasPerfectAccuracy() {
		return inaccuracy <= 0;
	}

	/**
	 * Gets the inaccuracy, taking into account Bullseye enchantment.<br>
	 * Accuracy is actually inaccuracy internally, because it's easier to math.<br>
	 * The formula is just accuracy = 1 / inaccuracy.
	 */
	public double getInaccuracy(ItemStack stack, @Nullable PlayerEntity player) {
		double nextInaccuracy = Math.max(0, inaccuracy / ((EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.bullseye, stack) * KGConfig.bullseyeAccuracyIncrease.get()) + 1.0));

		nextInaccuracy += shotsBeforeStability * Math.max(0, instabilitySpreadAdditional / ((EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.bullseye, stack) * KGConfig.bullseyeAccuracyIncrease.get()) + 1.0D));

		//check player hands
		if ((player != null) && (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.cowboy, stack) == 0)) {
			//if both hands are full, because one is the gun and one is something else
			if (!player.getMainHandItem().isEmpty() && !player.getOffhandItem().isEmpty()) {
				//if sniper class, give a new inaccuracy
				if (nextInaccuracy == 0) nextInaccuracy = KGConfig.oneHandInaccuracyReplacement.get();
				//else multiply
				else nextInaccuracy *= KGConfig.oneHandInaccuracyMultiplier.get();
			}
		}

		//check crouching
		if ((player != null) && (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.counterStrike, stack) != 0)) {
			if (player.isCrouching() && player.isOnGround()) {
				nextInaccuracy /= KGConfig.crouchAccuracyMultiplier.get();
			}
		}

		//inaccurate jump shotting, balancing grappling hooks
		if (player != null) {
			if (!player.isOnGround() && (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.movingShooter, stack) == 0)) {
				//if sniper class, give a new inaccuracy
				if (nextInaccuracy == 0) nextInaccuracy = KGConfig.midairInaccuracyReplacement.get();
				//else multiply
				else nextInaccuracy *= KGConfig.midairInaccuracyMultiplier.get();
			}
		}

		return nextInaccuracy;
	}

	/**
	 *
	 * Gets the projectile speed, taking into account Accelerator enchantment.
	 */
	public double getProjectileSpeed(ItemStack stack, @Nullable PlayerEntity player) {
		return Math.max(0, projectileSpeed + ((EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.accelerator, stack) * KGConfig.acceleratorSpeedIncrease.get() * projectileSpeed)));
	}

	/**
	 * Chance to actually CONSUME ammo, to properly multiply probabilities together.<br>
	 * Tooltip then does the math to display it nicely.
	 */
	public double getInverseChanceFreeShot(ItemStack stack, @Nullable PlayerEntity player) {
		double chance = 1 - chanceFreeShot;
		int preserving = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.preserving, stack);
		if (preserving >= 1) chance *= preserving * KGConfig.preservingRateIncrease.get();
		return chance;
	}

	/**
	 * Says if the damage is changed from base value. Used for tooltip.
	 */
	protected boolean isDamageModified(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.impact, stack) >= 1;
	}

	/**
	 * Says if the fire delay is changed from base value. Used for tooltip.
	 */
	protected boolean isFireDelayModified(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.sleightOfHand, stack) >= 1;
	}

	/**
	 * Says if the (in)accuracy is changed from base value. Used for tooltip.
	 */
	protected boolean isInaccuracyModified(ItemStack stack) {
		return !hasPerfectAccuracy() && EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.bullseye, stack) >= 1;
	}

	/**
	 * Says if the chance for free shots is changed from base value. Used for tooltip.
	 */
	protected boolean isChanceFreeShotModified(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.preserving, stack) >= 1;
	}

	protected boolean isProjectileSpeedModified(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.accelerator, stack) >= 1;
	}

	public int getComboCount() { return this.comboCount; }

	/**
	 * Sets whether the bullets ignore invulnerability frame (default no), used when making the item for registering.
	 */
	public GunItem ignoreInvulnerability(boolean ignoreInvulnerability) {
		this.ignoreInvulnerability = ignoreInvulnerability;
		return this;
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
	public GunItem fireSound(SoundEvent fireSound) {
		this.fireSound = fireSound;
		return this;
	}

	/**
	 * Sets the reload sound, used when making the item for registering.
	 */
	public GunItem reloadSound(SoundEvent newReloadSound) {
		this.reloadSound = newReloadSound;
		return this;
	}

	/**
	 * Sets a projectile speed, used when making the item for registering.<br>
	 * Base value is 3.
	 */
	public GunItem projectileSpeed(double projectileSpeed) {
		this.projectileSpeed = projectileSpeed;
		return this;
	}

	/**
	 * Gives the gun the ability to break blocks.
	 */
	public GunItem canMineBlocks(boolean mineBlocks){
		this.hasBlockMineAbility = mineBlocks;
		return this;
	}

	/**
	 * Sets the repair material, used when making the item for registering.
	 */
	public GunItem repair(Supplier<Ingredient> repairMaterial) {
		this.repairMaterial = repairMaterial;
		return this;
	}

	public GunItem chambers(int revolverCount) {
		this.revolutions = revolverCount;
		this.chamber = this.revolutions;
		return this;
	}

	public GunItem collateral(boolean collateralSetting) {
		this.shouldCollateral = collateralSetting;
		return this;
	}

	public GunItem setKnockbackStrength(double newKnockback) {
		this.myKnockback = newKnockback;
		return this;
	}

	public GunItem instabilityAdditionalSpread(double additionalSpread) {
		this.instabilitySpreadAdditional = additionalSpread;
		return this;
	}

	public GunItem setStabilityTime(int stability) {
		this.stabilityTime = stability;
		return this;
	}

	public GunItem setShouldCombo(boolean combo) {
		this.shouldCombo = combo;
		return this;
	}

	public GunItem setIsExplosive(boolean explosive) {
		this.isExplosive = explosive;
		return this;
	}

	public GunItem setIsWither(boolean wither) {
		this.isWither = wither;
		return this;
	}

	public GunItem setShouldRevenge(boolean revenge) {
		this.shouldRevenge = revenge;
		return this;
	}

	/**
	 *
	 * @param barrelSwitch set the divider that divides the fire rate to denote how many ticks it takes to switch barrels
	 *
	 */
	public GunItem setBarrelSwitchSpeed(int barrelSwitch) {
		this.barrelSwitchSpeed = barrelSwitch;
		return this;
	}

	/**
	 *
	 * @param victim the victim entity to see if it has been comboed.  give it the shooter if no entity was hit.
	 * @return returns a multiplier that should multiply the damage.
	 */
	public double tryComboCalculate(UUID victim, PlayerEntity shooter) {
		if (victim == shooter.getUUID()){
			comboVictim = null;
			comboCount = 0;
		}
		else if (comboVictim == null) {
			comboVictim = victim;
			comboCount = 1;
		}
		else if (victim == comboVictim) {
			comboCount++;
		}
		else
		{
			comboCount = 0;
			comboVictim = null;
		}

		//cap combo
		if (comboCount > KGConfig.goldSkillshotMaxCombo.get()) comboCount = KGConfig.goldSkillshotMaxCombo.get();

		//calculate what the new multiplier would be.
		double newDamageMultiplier = this.damageMultiplier + (KGConfig.goldSkillshotComboMultiplierPer.get() * comboCount);

		return newDamageMultiplier;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		if (EnchantmentHelper.getEnchantments(stack).size() + EnchantmentHelper.getEnchantments(book).size() > 4) return false;

		return super.isBookEnchantable(stack, book);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		//Disallow these for specific gun types
		GunItem me = (GunItem) stack.getItem();
		if (enchantment == ModEnchantments.bullseye && hasPerfectAccuracy()) return false; //not for sniper
		if (enchantment == ModEnchantments.counterStrike && hasPerfectAccuracy()) return false; //not for sniper
		if (enchantment == ModEnchantments.impact && isExplosive) return false; //these are not for launcher
		if (enchantment == ModEnchantments.luckyShot && isExplosive) return false;
		if (enchantment == ModEnchantments.frostShot && isExplosive) return false;

		//only let these apply to certain gun types
		if (enchantment == ModEnchantments.division && !(me instanceof ShotgunItem)) return false; //shotgun only
		if (enchantment == ModEnchantments.marker && ((me instanceof ShotgunItem) || (me instanceof GatlingItem) || (me.isExplosive) || (me.getInaccuracy(stack, null) != 0))) return false; //pistol only
		if (enchantment == ModEnchantments.maneuvering && !(me instanceof GatlingItem)) return false; //gatling only

		//Disallow these if other enchantments are already applied.
		//impact versus frost shot
		if ((enchantment == ModEnchantments.impact)
				&& (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.luckyShot, stack) != 0)
				&& (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.frostShot, stack) != 0)) return false;
		if ((enchantment == ModEnchantments.luckyShot)
				&& (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.impact, stack) != 0)
				&& (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.frostShot, stack) != 0)) return false;
		if ((enchantment == ModEnchantments.frostShot)
				&& (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.luckyShot, stack) != 0)
				&& (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.impact, stack) != 0)) return false;

		//bullseye versus counterstrike
		if ((enchantment == ModEnchantments.bullseye)
				&& (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.counterStrike, stack) != 0)) return false;
		if ((enchantment == ModEnchantments.counterStrike)
				&& (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.bullseye, stack) != 0)) return false;

		return super.canApplyAtEnchantingTable(stack, enchantment);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			//Damage
			double damageMultiplier = getDamageMultiplier(stack);
			double damageBonus = getBonusDamage(stack, null) * damageMultiplier;

			if (damageMultiplier != 1) {
				if (damageBonus != 0) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.gun.damage.both" + (isDamageModified(stack) ? ".modified" : ""), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageMultiplier), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageBonus)));
				else tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.gun.damage.mult" + (isDamageModified(stack) ? ".modified" : ""), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageMultiplier)));
			}
			else if (damageBonus != 0) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.gun.damage.flat" + (isDamageModified(stack) ? ".modified" : ""), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(damageBonus)));

			//Fire rate
			int fireDelay = getFireDelay(stack, null);
			tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.gun.firerate" + (isFireDelayModified(stack) ? ".modified" : ""), fireDelay, (60*20) / fireDelay));

			//Accuracy
			double inaccuracy = getInaccuracy(stack, null);
			if (inaccuracy <= 0) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.gun.accuracy.perfect" + (isInaccuracyModified(stack) ? ".modified" : "")));
			else tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.gun.accuracy" + (isInaccuracyModified(stack) ? ".modified" : ""), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(1.0 / inaccuracy)));

			//Projectile Speed
			double projectileSpeed = getProjectileSpeed(stack, null);
			tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.gun.speed" + (isProjectileSpeedModified(stack) ? ".modified" : ""), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(projectileSpeed)));

			//Chance to not consume ammo
			double inverseChanceFree = getInverseChanceFreeShot(stack, null);
			if (inverseChanceFree < 1) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.gun.chance_free" + (isChanceFreeShotModified(stack) ? ".modified" : ""), (int)((1 - inverseChanceFree) * 100)));

			if (hasBlockMineAbility) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.minegun"));

			if (shouldCollateral) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.collateral"));

			if (revolutions > 1) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.double_barrel"));
			if (stack.getItem() == ModItems.revolver) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.revolver"));
			if (shouldCombo) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.skill_shot"));
			if (isWither) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.wither"));
			if (shouldRevenge) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.blessed"));
			if (this.getItem() == ModItems.plasmaGatling) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.plasma"));

			if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.frostShot, stack) > 0) tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.frost_distance", EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.frostShot, stack) * KGConfig.frostyDistancePerLevel.get()));

			addExtraStatsTooltip(stack, world, tooltip);
		}
		else tooltip.add(new TranslationTextComponent("tooltip.kaleidiosguns.shift"));
	}

	/**
	 * Add more tooltips that will be displayed below the base stats.
	 */
	protected void addExtraStatsTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip) {

	}

	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.BOW;
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
		return 15;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return (repairMaterial != null && repairMaterial.get().test(repair)) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !ItemStack.isSameIgnoreDurability(oldStack, newStack);
	}

	private void shootProjectile(ProjectileEntity entity, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
		float f = -MathHelper.sin(pY * ((float)Math.PI / 180F)) * MathHelper.cos(pX * ((float)Math.PI / 180F));
		float f1 = -MathHelper.sin((pX + pZ) * ((float)Math.PI / 180F));
		float f2 = MathHelper.cos(pY * ((float)Math.PI / 180F)) * MathHelper.cos(pX * ((float)Math.PI / 180F));
		entity.shoot(f, f1, f2, pVelocity, pInaccuracy);
	}

	@Override
	public int getUseDuration(ItemStack pStack) {
		return Math.max(getFireDelay(pStack, null) / 20, 10);
	}
}
