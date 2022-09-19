package xyz.kaleidiodev.kaleidiosguns.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;

public class GunDamageEnchantment extends GunEnchantment {

    public GunDamageEnchantment(Rarity rarityIn, int maxLevel, int minCost, int levelCost, int levelCostSpan, EnchantmentType enchantmentType) {
        super(rarityIn, maxLevel, minCost, levelCost, levelCostSpan, enchantmentType);
    }

    @Override
    protected boolean checkCompatibility(Enchantment pEnchant) {
        if (pEnchant instanceof GunDamageEnchantment) return false;
        else return true;
    }
}
