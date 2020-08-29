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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GunItem extends ShootableItem {
	private int bonusDamage;
	private double damageMultiplier;
	private int fireDelay;
	private double inaccuracy;
	private int enchantability;

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
				BulletEntity shot = bulletItem.createProjectile(world, ammo, player);
				shot.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0, (float)getProjectileSpeed(gun, player), (float)getInaccuracy(gun, player));
				shot.setDamage((shot.getDamage() + getBonusDamage(gun, player)) * getDamageMultiplier(gun, player));

				gun.damageItem(1, player, (p) -> p.sendBreakAnimation(player.getActiveHand()));
				world.addEntity(shot);

				if (!bulletFree) bulletItem.consume(ammo, player);
			}

			world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 1.5F);
			player.addStat(Stats.ITEM_USED.get(this));

			player.getCooldownTracker().setCooldown(this, getFireDelay(gun, player));
			return ActionResult.resultConsume(gun);
		}
		else return ActionResult.resultFail(gun);
	}
	
	public boolean shouldConsumeAmmo(ItemStack stack, PlayerEntity player) {
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
	
	public double getProjectileSpeed(ItemStack stack, @Nullable PlayerEntity player) {
		return 3 * (1 + 0.25 * EnchantmentHelper.getEnchantmentLevel(ModEnchantments.bullseye, stack));
	}
	
	public double getInaccuracy(ItemStack stack, @Nullable PlayerEntity player) {
		//TODO when it's low (Bullseye II or III on the iron gun) the shots get rounding errors or something, investigate
		return inaccuracy / (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.bullseye, stack) + 1.0);
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

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			double damageMultiplier = getDamageMultiplier(stack, null);
			double damageBonus = getBonusDamage(stack, null) * damageMultiplier;
			
			if (damageMultiplier != 1) {
				if (damageBonus != 0) tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.damage.both" + (isDamageModified(stack) ? ".modified" : ""), String.format(Locale.ROOT, "%.2f", damageMultiplier), String.format(Locale.ROOT, "%.2f", damageBonus)));
				else tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.damage.mult" + (isDamageModified(stack) ? ".modified" : ""), String.format(Locale.ROOT, "%.2f", damageMultiplier)));
			}
			else if (damageBonus != 0) tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.damage.flat" + (isDamageModified(stack) ? ".modified" : ""), String.format(Locale.ROOT, "%.2f", damageBonus)));
			
			int fireDelay = getFireDelay(stack, null);
			tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.firerate" + (isFireDelayModified(stack) ? ".modified" : ""), fireDelay, (60*20) / fireDelay));
			
			double inaccuracy = getInaccuracy(stack, null);
			if (inaccuracy <= 0) tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.accuracy.perfect" + (isInaccuracyModified(stack) ? ".modified" : "")));
			else tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.gun.accuracy" + (isInaccuracyModified(stack) ? ".modified" : ""), String.format(Locale.ROOT, "%.2f", 1.0 / inaccuracy)));
		}
		else tooltip.add(new TranslationTextComponent("tooltip.gunswithoutroses.shift"));
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
