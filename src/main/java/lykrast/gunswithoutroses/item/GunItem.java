package lykrast.gunswithoutroses.item;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.ModEnchantments;
import lykrast.gunswithoutroses.ModItems;
import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GunItem extends ShootableItem {
	protected int bonusDamage;
	protected double damageMultiplier;
	protected int fireDelay;
	protected double inaccuracy;
	private int enchantability;
	protected boolean ignoreInvulnerability = false;
	protected double chanceFreeShot = 0;

	public GunItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability) {
		super(properties);
		this.bonusDamage = bonusDamage;
		this.damageMultiplier = damageMultiplier;
		this.enchantability = enchantability;
		this.fireDelay = fireDelay;
		this.inaccuracy = inaccuracy;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack gun = player.getHeldItem(hand);
		ItemStack ammo = player.findAmmo(gun);

		if (!ammo.isEmpty() || player.abilities.isCreativeMode) {
			if (ammo.isEmpty() || ammo.getItem() == Items.ARROW) {
				ammo = new ItemStack(ModItems.flintBullet);
			}

			BulletItem bulletItem = (BulletItem) (ammo.getItem() instanceof BulletItem ? ammo.getItem() : ModItems.flintBullet);
			if (!world.isRemote) {
				boolean bulletFree = player.abilities.isCreativeMode || !shouldConsumeAmmo(gun, player);

				shoot(world, player, gun, ammo, bulletItem, bulletFree);
				
				gun.damageItem(1, player, (p) -> p.sendBreakAnimation(player.getActiveHand()));
				if (!bulletFree) bulletItem.consume(ammo, player);
			}

			world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
			player.addStat(Stats.ITEM_USED.get(this));

			player.getCooldownTracker().setCooldown(this, getFireDelay(gun, player));
			return ActionResult.resultConsume(gun);
		}
		else return ActionResult.resultFail(gun);
	}
	
	protected void shoot(World world, PlayerEntity player, ItemStack gun, ItemStack ammo, BulletItem bulletItem, boolean bulletFree) {
		BulletEntity shot = bulletItem.createProjectile(world, ammo, player);
		shot.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0, 3, (float)getInaccuracy(gun, player));
		shot.setDamage((shot.getDamage() + getBonusDamage(gun, player)) * getDamageMultiplier(gun, player));
		shot.setIgnoreInvulnerability(ignoreInvulnerability);

		world.addEntity(shot);
	}
	
	public boolean shouldConsumeAmmo(ItemStack stack, PlayerEntity player) {
		if (chanceFreeShot > 0 && random.nextDouble() < chanceFreeShot) return false;
		
		int preserving = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.preserving, stack);
		//(level) in (level + 2) chance to not consume
		if (preserving >= 1 && random.nextInt(preserving + 2) >= 2) return false;
		
		return true;
	}
	
	public double getBonusDamage(ItemStack stack, @Nullable PlayerEntity player) {
		int impact = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.impact, stack);
		return bonusDamage + (impact >= 1 ? 0.5 * (impact + 1) : 0);
	}
	
	public double getDamageMultiplier(ItemStack stack, @Nullable PlayerEntity player) {
		return damageMultiplier;
	}
	
	public int getFireDelay(ItemStack stack, @Nullable PlayerEntity player) {
		return Math.max(1, fireDelay - (int)(fireDelay * EnchantmentHelper.getEnchantmentLevel(ModEnchantments.sleightOfHand, stack) * 0.15));
	}
	
	public double getInaccuracy(ItemStack stack, @Nullable PlayerEntity player) {
		return inaccuracy / (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.bullseye, stack) + 1.0);
	}
	
	//Chance to actually consume ammo, to properly multiply probabilities together
	//Tooltip then does the math to display it nicely
	public double getInverseChanceFreeShot(ItemStack stack, @Nullable PlayerEntity player) {
		double chance = 1 - chanceFreeShot;
		int preserving = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.preserving, stack);
		if (preserving >= 1) chance *= 2.0/(preserving + 2);
		return chance;
	}
	
	protected boolean isDamageModified(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(ModEnchantments.impact, stack) >= 1;
	}
	
	protected boolean isFireDelayModified(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(ModEnchantments.sleightOfHand, stack) >= 1;
	}
	
	protected boolean isInaccuracyModified(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(ModEnchantments.bullseye, stack) >= 1;
	}
	
	protected boolean isChanceFreeShotModified(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(ModEnchantments.preserving, stack) >= 1;
	}
	
	public GunItem ignoreInvulnerability(boolean ignoreInvulnerability) {
		this.ignoreInvulnerability = ignoreInvulnerability;
		return this;
	}
	
	public GunItem chanceFreeShot(double chanceFreeShot) {
		this.chanceFreeShot = chanceFreeShot;
		return this;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		//Disallow Bullseye if the gun has perfect accuracy
		if (enchantment == ModEnchantments.bullseye && inaccuracy <= 0) return false;
		return super.canApplyAtEnchantingTable(stack, enchantment);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			//Damage
			double damageMultiplier = getDamageMultiplier(stack, null);
			double damageBonus = getBonusDamage(stack, null) * damageMultiplier;
			
			if (damageMultiplier != 1) {
				if (damageBonus != 0) tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.damage.both" + (isDamageModified(stack) ? ".modified" : ""), String.format(Locale.ROOT, "%.2f", damageMultiplier), String.format(Locale.ROOT, "%.2f", damageBonus)));
				else tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.damage.mult" + (isDamageModified(stack) ? ".modified" : ""), String.format(Locale.ROOT, "%.2f", damageMultiplier)));
			}
			else if (damageBonus != 0) tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.damage.flat" + (isDamageModified(stack) ? ".modified" : ""), String.format(Locale.ROOT, "%.2f", damageBonus)));
			
			//Fire rate
			int fireDelay = getFireDelay(stack, null);
			tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.firerate" + (isFireDelayModified(stack) ? ".modified" : ""), fireDelay, (60*20) / fireDelay));
			
			//Accuracy
			double inaccuracy = getInaccuracy(stack, null);
			if (inaccuracy <= 0) tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.accuracy.perfect" + (isInaccuracyModified(stack) ? ".modified" : "")));
			else tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.accuracy" + (isInaccuracyModified(stack) ? ".modified" : ""), String.format(Locale.ROOT, "%.2f", 1.0 / inaccuracy)));
			
			//Chance to not consume ammo
			double inverseChanceFree = getInverseChanceFreeShot(stack, null);
			if (inverseChanceFree < 1) tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.chance_free" + (isChanceFreeShotModified(stack) ? ".modified" : ""), (int)((1 - inverseChanceFree) * 100)));
			
			//Other stats
			if (ignoreInvulnerability) tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.ignore_invulnerability").func_240699_a_(TextFormatting.GRAY));
			
			addExtraStatsTooltip(stack, world, tooltip);
		}
		else tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.shift"));
	}
	
	protected void addExtraStatsTooltip(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip) {
		
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public int getItemEnchantability() {
		return enchantability;
	}

	private static final Predicate<ItemStack> BULLETS = (stack) -> stack.getItem() instanceof BulletItem;

	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return BULLETS;
	}

	@Override
	public int func_230305_d_() {
		// No idea what this is yet, so using the Bow value (Crossbow is 8)
		return 15;
	}

}
