package lykrast.gunswithoutroses.enchantment;

import lykrast.gunswithoutroses.registry.ModEnchantments;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class GunEnchantment extends Enchantment {
	private int maxLevel, minCost, levelCost, levelCostSpan;

	public GunEnchantment(Rarity rarityIn, int maxLevel, int minCost, int levelCost, int levelCostSpan) {
		super(rarityIn, ModEnchantments.TYPE_GUN, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
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
