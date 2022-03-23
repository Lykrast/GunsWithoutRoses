package lykrast.gunswithoutroses.enchantment;

import lykrast.gunswithoutroses.registry.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;

public class GunEnchantment extends Enchantment {

	private final int maxLevel;
	private final int minCost;
	private final int levelCost;
	private final int levelCostSpan;

	public GunEnchantment(Rarity rarityIn, int maxLevel, int minCost, int levelCost, int levelCostSpan) {
		super(rarityIn, ModEnchantments.TYPE_GUN, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });
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
