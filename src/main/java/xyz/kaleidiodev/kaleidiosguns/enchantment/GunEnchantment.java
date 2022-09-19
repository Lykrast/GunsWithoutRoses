package xyz.kaleidiodev.kaleidiosguns.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.Mod;
import xyz.kaleidiodev.kaleidiosguns.registry.ModEnchantments;

public class GunEnchantment extends Enchantment {

	private final int maxLevel;
	private final int minCost;
	private final int levelCost;
	private final int levelCostSpan;

	public GunEnchantment(Rarity rarityIn, int maxLevel, int minCost, int levelCost, int levelCostSpan, EnchantmentType enchantmentType) {
		super(rarityIn, enchantmentType, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });
		this.maxLevel = maxLevel;
		this.minCost = minCost;
		this.levelCost = levelCost;
		this.levelCostSpan = levelCostSpan;
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return minCost + (enchantmentLevel - 1) * levelCost;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return getMinCost(enchantmentLevel) + levelCostSpan;
	}

	@Override
	public int getDamageProtection(int level, DamageSource source) {
		return 0;
	}

}
