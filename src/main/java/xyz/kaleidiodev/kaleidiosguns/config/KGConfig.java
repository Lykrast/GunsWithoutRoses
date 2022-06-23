package xyz.kaleidiodev.kaleidiosguns.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.lwjgl.system.CallbackI;

public class KGConfig {

    public static ForgeConfigSpec spec;

    //Guns
    public static ForgeConfigSpec.DoubleValue ironPistolDamageMultiplier;
    public static ForgeConfigSpec.IntValue ironPistolFireDelay;
    public static ForgeConfigSpec.IntValue ironPistolEnchantability;
    public static ForgeConfigSpec.IntValue ironPistolDurability;
    public static ForgeConfigSpec.DoubleValue ironPistolInaccuracy;
    public static ForgeConfigSpec.DoubleValue ironPistolProjectileSpeed;

    public static ForgeConfigSpec.DoubleValue goldRevolverDamageMultiplier;
    public static ForgeConfigSpec.IntValue goldRevolverFireDelay;
    public static ForgeConfigSpec.IntValue goldRevolverEnchantability;
    public static ForgeConfigSpec.IntValue goldRevolverDurability;
    public static ForgeConfigSpec.DoubleValue goldRevolverInaccuracy;
    public static ForgeConfigSpec.DoubleValue goldRevolverProjectileSpeed;
    public static ForgeConfigSpec.DoubleValue goldRevolverSpreadoutStrength;
    public static ForgeConfigSpec.IntValue goldRevolverChamberSwitchSpeed;
    public static ForgeConfigSpec.IntValue goldRevolverStabilityTime;

    public static ForgeConfigSpec.DoubleValue diamondSkillshotDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondSkillshotFireDelay;
    public static ForgeConfigSpec.IntValue diamondSkillshotEnchantability;
    public static ForgeConfigSpec.IntValue diamondSkillshotDurability;
    public static ForgeConfigSpec.DoubleValue diamondSkillshotInaccuracy;
    public static ForgeConfigSpec.DoubleValue diamondSkillshotProjectileSpeed;
    public static ForgeConfigSpec.IntValue diamondSkillshotMaxCombo;
    public static ForgeConfigSpec.DoubleValue diamondSkillshotComboMultiplierPer;

    public static ForgeConfigSpec.DoubleValue diamondShotgunDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondShotgunFireDelay;
    public static ForgeConfigSpec.IntValue diamondShotgunEnchantability;
    public static ForgeConfigSpec.IntValue diamondShotgunDurability;
    public static ForgeConfigSpec.DoubleValue diamondShotgunInaccuracy;
    public static ForgeConfigSpec.IntValue diamondShotgunBulletCount;
    public static ForgeConfigSpec.DoubleValue diamondShotgunProjectileSpeed;

    public static ForgeConfigSpec.DoubleValue goldDoubleShotgunDamageMultiplier;
    public static ForgeConfigSpec.IntValue goldDoubleShotgunFireDelay;
    public static ForgeConfigSpec.IntValue goldDoubleShotgunEnchantability;
    public static ForgeConfigSpec.IntValue goldDoubleShotgunDurability;
    public static ForgeConfigSpec.DoubleValue goldDoubleShotgunInaccuracy;
    public static ForgeConfigSpec.IntValue goldDoubleShotgunBulletCount;
    public static ForgeConfigSpec.DoubleValue goldDoubleShotgunProjectileSpeed;
    public static ForgeConfigSpec.IntValue goldDoubleShotgunChamberSwitchSpeed;
    public static ForgeConfigSpec.DoubleValue goldDoubleShotgunKnockback;

    public static ForgeConfigSpec.DoubleValue diamondSniperDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondSniperFireDelay;
    public static ForgeConfigSpec.IntValue diamondSniperEnchantability;
    public static ForgeConfigSpec.IntValue diamondSniperDurability;
    public static ForgeConfigSpec.DoubleValue diamondSniperInaccuracy;
    public static ForgeConfigSpec.DoubleValue diamondSniperProjectileSpeed;

    public static ForgeConfigSpec.DoubleValue ironCarbineDamageMultiplier;
    public static ForgeConfigSpec.IntValue ironCarbineFireDelay;
    public static ForgeConfigSpec.IntValue ironCarbineEnchantability;
    public static ForgeConfigSpec.IntValue ironCarbineDurability;
    public static ForgeConfigSpec.DoubleValue ironCarbineInaccuracy;
    public static ForgeConfigSpec.DoubleValue ironCarbineProjectileSpeed;

    public static ForgeConfigSpec.DoubleValue goldLauncherDamageMultiplier;
    public static ForgeConfigSpec.IntValue goldLauncherFireDelay;
    public static ForgeConfigSpec.IntValue goldLauncherEnchantability;
    public static ForgeConfigSpec.IntValue goldLauncherDurability;
    public static ForgeConfigSpec.DoubleValue goldLauncherInaccuracy;
    public static ForgeConfigSpec.DoubleValue goldLauncherProjectileSpeed;

    public static ForgeConfigSpec.DoubleValue diamondMinegunDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondMinegunFireDelay;
    public static ForgeConfigSpec.IntValue diamondMinegunEnchantability;
    public static ForgeConfigSpec.IntValue diamondMinegunDurability;
    public static ForgeConfigSpec.DoubleValue diamondMinegunInaccuracy;
    public static ForgeConfigSpec.DoubleValue diamondMinegunProjectileSpeed;
    public static ForgeConfigSpec.DoubleValue diamondMinegunMineChance;

    public static ForgeConfigSpec.DoubleValue ironAssaultDamageMultiplier;
    public static ForgeConfigSpec.IntValue ironAssaultFireDelay;
    public static ForgeConfigSpec.IntValue ironAssaultEnchantability;
    public static ForgeConfigSpec.IntValue ironAssaultDurability;
    public static ForgeConfigSpec.DoubleValue ironAssaultInaccuracy;
    public static ForgeConfigSpec.DoubleValue ironAssaultProjectileSpeed;

    public static ForgeConfigSpec.DoubleValue goldStreamDamageMultiplier;
    public static ForgeConfigSpec.IntValue goldStreamFireDelay;
    public static ForgeConfigSpec.IntValue goldStreamEnchantability;
    public static ForgeConfigSpec.IntValue goldStreamDurability;
    public static ForgeConfigSpec.DoubleValue goldStreamInaccuracy;
    public static ForgeConfigSpec.DoubleValue goldStreamProjectileSpeed;
    public static ForgeConfigSpec.DoubleValue goldStreamShieldAdditional;

    //Bullets
    public static ForgeConfigSpec.IntValue flintBulletDamage;
    public static ForgeConfigSpec.IntValue ironBulletDamage;
    public static ForgeConfigSpec.IntValue blazeBulletDamage;
    public static ForgeConfigSpec.IntValue hungerBulletDamage;

    //Minegun balance curve
    public static ForgeConfigSpec.DoubleValue mineGunSecondLevel;
    public static ForgeConfigSpec.DoubleValue mineGunThirdLevel;
    public static ForgeConfigSpec.DoubleValue mineGunFourthLevel;
    public static ForgeConfigSpec.DoubleValue mineGunFifthLevel;

    //enchantment related mechanics
    public static ForgeConfigSpec.DoubleValue impactDamageIncrease;
    public static ForgeConfigSpec.DoubleValue bullseyeAccuracyIncrease;
    public static ForgeConfigSpec.DoubleValue sleightOfHandFireRateDecrease;
    public static ForgeConfigSpec.DoubleValue preservingRateIncrease;
    public static ForgeConfigSpec.DoubleValue acceleratorSpeedIncrease;
    public static ForgeConfigSpec.DoubleValue passionForBloodRateIncrease;
    public static ForgeConfigSpec.DoubleValue passionForBloodHealIncrease;
    public static ForgeConfigSpec.IntValue divisionCountIncrease;
    public static ForgeConfigSpec.DoubleValue puncturingMultiplier;
    public static ForgeConfigSpec.DoubleValue luckyShotChance;
    public static ForgeConfigSpec.DoubleValue criticalDamage;
    public static ForgeConfigSpec.DoubleValue oneHandInaccuracyMultiplier;
    public static ForgeConfigSpec.DoubleValue oneHandInaccuracyReplacement;


    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Kaleidio's Guns Config");
        builder.comment("Please note that the below options may effect the balance, as usually values cap at 100%.  higher values may not work as expected, or the curve of balance may flatten at the top.");

        builder.push("pistols");
        ironPistolDamageMultiplier = builder
                .comment("Define the Damage multiplier for Pistols")
                .defineInRange("ironPistolDamageModifier", 1D, 0.1D, 5D);
        ironPistolFireDelay = builder
                .comment("Configure the Fire delay for Pistols")
                .defineInRange("ironPistolFireDelay", 12, 0, 72000);
        ironPistolEnchantability = builder
                .comment("Configure the Enchantability for Pistols")
                .defineInRange("ironPistolEnchantability", 14, 0, 30);
        ironPistolDurability = builder
                .comment("Configure the Durability for Pistols")
                .defineInRange("ironPistolDurability", 4000, 0, 32767);
        ironPistolInaccuracy = builder
                .comment("Configure the Inaccuracy for Pistols")
                .defineInRange("ironPistolInaccuracy", 2.0D, 0D, 90D);
        ironPistolProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Pistols")
                .defineInRange("ironPistolProjectileSpeed", 8D, 0D, 64D);

        goldRevolverDamageMultiplier = builder
                .comment("Define the Damage multiplier for Revolver")
                .defineInRange("goldRevolverDamageModifier", 1.75D, 0.1D, 5D);
        goldRevolverFireDelay = builder
                .comment("Configure the Fire delay for Revolver")
                .defineInRange("goldRevolverFireDelay", 54, 0, 72000);
        goldRevolverEnchantability = builder
                .comment("Configure the Enchantability for Revolver")
                .defineInRange("goldRevolverEnchantability", 23, 0, 30);
        goldRevolverDurability = builder
                .comment("Configure the Durability for Revolver")
                .defineInRange("goldRevolverDurability", 900, 0, 32767);
        goldRevolverInaccuracy = builder
                .comment("Configure the Inaccuracy for Revolver")
                .defineInRange("goldRevolverInaccuracy", 2.5D, 0D, 90D);
        goldRevolverProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Revolver")
                .defineInRange("goldRevolverProjectileSpeed", 9D, 0D, 64D);
        goldRevolverSpreadoutStrength = builder
                .comment("Configure the strength a Revolver increases its spread cone every time it's used too fast")
                .defineInRange("goldRevolverSpreadoutStrength", 0.75D, 0D, 64D);
        goldRevolverChamberSwitchSpeed = builder
                .comment("Configure a divider of base fire delay how long switching chambers takes between shots on Revolvers")
                .defineInRange("goldRevolverChamberSwitchSpeed", 3, 1, 72000);
        goldRevolverStabilityTime = builder
                .comment("Configure how long the gun must not be fired for spread cone to stabilize on Revolver")
                .defineInRange("goldRevolverStabilityTime", 28, 1, 72000);

        diamondSkillshotDamageMultiplier = builder
                .comment("Define the Damage multiplier for Skillshot Pistols")
                .defineInRange("diamondSkillshotDamageModifier", 1.0D, 0.1D, 5D);
        diamondSkillshotFireDelay = builder
                .comment("Configure the Fire delay for Skillshot Pistols")
                .defineInRange("diamondSkillshotFireDelay", 20, 0, 72000);
        diamondSkillshotEnchantability = builder
                .comment("Configure the Enchantability for Skillshot Pistols")
                .defineInRange("diamondSkillshotEnchantability", 10, 0, 30);
        diamondSkillshotDurability = builder
                .comment("Configure the Durability for Skillshot Pistols")
                .defineInRange("diamondSkillshotDurability", 4000, 0, 32767);
        diamondSkillshotInaccuracy = builder
                .comment("Configure the Inaccuracy for Skillshot Pistols")
                .defineInRange("diamondSkillshotInaccuracy", 4.0D, 0D, 90D);
        diamondSkillshotProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Skillshot Pistols")
                .defineInRange("diamondSkillshotProjectileSpeed", 6D, 0D, 64D);
        diamondSkillshotMaxCombo = builder
                .comment("Configure the maximum combo on Skillshot Pistols")
                .defineInRange("diamondSkillshotMaxCombo", 5, 2, 255);
        diamondSkillshotComboMultiplierPer = builder
                .comment("Configure the addition to damage multiplier per combo for Skillshot Pistols")
                .defineInRange("diamondSkillshotComboMultplierPer", 0.2D, 0D, 64D);
        builder.pop();

        builder.push("shotguns");
        diamondShotgunDamageMultiplier = builder
                .comment("Define the Damage multiplier for Shotguns")
                .defineInRange("diamondShotgunDamageMultiplier", 0.4D, 0.1D, 5D);
        diamondShotgunFireDelay = builder
                .comment("Configure the Fire delay for Shotguns")
                .defineInRange("diamondShotgunFireDelay", 24, 0, 72000);
        diamondShotgunEnchantability = builder
                .comment("Configure the Enchantability for Shotguns")
                .defineInRange("diamondShotgunEnchantability", 10, 0, 30);
        diamondShotgunDurability = builder
                .comment("Configure the Durability for Shotguns")
                .defineInRange("diamondShotgunDurability", 700, 0, 32767);
        diamondShotgunInaccuracy = builder
                .comment("Configure the Inaccuracy for Shotguns")
                .defineInRange("diamondShotgunInaccuracy", 5.0D, 0D, 90D);
        diamondShotgunBulletCount = builder
                .comment("Configure the amount of Bullets at once for Shotguns")
                .defineInRange("diamondShotgunBulletCount", 5, 0, 50);
        diamondShotgunProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Shotguns")
                .defineInRange("diamondShotgunProjectileSpeed", 8D, 0D, 64D);

        goldDoubleShotgunDamageMultiplier = builder
                .comment("Define the Damage multiplier for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunDamageMultiplier", 0.4D, 0.1D, 5D);
        goldDoubleShotgunFireDelay = builder
                .comment("Configure the Fire delay for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunFireDelay", 60, 0, 72000);
        goldDoubleShotgunEnchantability = builder
                .comment("Configure the Enchantability for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunEnchantability", 23, 0, 30);
        goldDoubleShotgunDurability = builder
                .comment("Configure the Durability for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunDurability", 300, 0, 32767);
        goldDoubleShotgunInaccuracy = builder
                .comment("Configure the Inaccuracy for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunInaccuracy", 8.5D, 0D, 90D);
        goldDoubleShotgunBulletCount = builder
                .comment("Configure the amount of Bullets at once for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunBulletCount", 5, 0, 100);
        goldDoubleShotgunProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunProjectileSpeed", 8D, 0D, 64D);
        goldDoubleShotgunChamberSwitchSpeed = builder
                .comment("Configure how long switching chambers takes between shots on Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunChamberSwitchSpeed", 15, 1, 72000);
        goldDoubleShotgunKnockback = builder
                .comment("Configure how strong knockback is at closest range on Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunKnockback", 1.2D, 0.01D, 5D);
        builder.pop();

        builder.push("rifles");
        diamondSniperDamageMultiplier = builder
                .comment("Define the Damage multiplier for Snipers")
                .defineInRange("diamondSniperDamageMultiplier", 2.5D, 0.1D, 5D);
        diamondSniperFireDelay = builder
                .comment("Configure the Fire delay for Snipers")
                .defineInRange("diamondSniperFireDelay", 48, 0, 72000);
        diamondSniperEnchantability = builder
                .comment("Configure the Enchantability for Snipers")
                .defineInRange("diamondSniperEnchantability", 10, 0, 30);
        diamondSniperDurability = builder
                .comment("Configure the Durability for Snipers")
                .defineInRange("diamondSniperDurability", 400, 0, 32767);
        diamondSniperInaccuracy = builder
                .comment("Configure the Inaccuracy for Snipers")
                .defineInRange("diamondSniperInaccuracy", 0D, 0D, 90D);
        diamondSniperProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Snipers")
                .defineInRange("diamondSniperProjectileSpeed", 16D, 0D, 64D);

        ironCarbineDamageMultiplier = builder
                .comment("Define the Damage multiplier for Carbines")
                .defineInRange("ironCarbineDamageMultiplier", 1.5D, 0.5D, 5D);
        ironCarbineFireDelay = builder
                .comment("Configure the Fire delay for Carbines")
                .defineInRange("ironCarbineFireDelay", 24, 0, 72000);
        ironCarbineEnchantability = builder
                .comment("Configure the Enchantability for Carbines")
                .defineInRange("ironCarbineEnchantability", 14, 0, 30);
        ironCarbineDurability = builder
                .comment("Configure the Durability for Carbines")
                .defineInRange("ironCarbineDurability", 1400, 0, 32767);
        ironCarbineInaccuracy = builder
                .comment("Configure the Inaccuracy for Carbines")
                .defineInRange("ironCarbineInaccuracy", 0D, 0D, 90D);
        ironCarbineProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Carbines")
                .defineInRange("ironCarbineProjectileSpeed", 12D, 0D, 64D);
        builder.pop();

        builder.push("automatics");
        diamondMinegunDamageMultiplier = builder
                .comment("Define the Damage multiplier for Mineguns")
                .defineInRange("diamondMinegunDamageMultiplier", 0.5D, 0.1D, 5D);
        diamondMinegunFireDelay = builder
                .comment("Configure the Fire delay for Mineguns")
                .defineInRange("diamondMinegunFireDelay", 10, 0, 72000);
        diamondMinegunEnchantability = builder
                .comment("Configure the Enchantability for Mineguns")
                .defineInRange("diamondMinegunEnchantability", 10, 0, 30);
        diamondMinegunDurability = builder
                .comment("Configure the Durability for Mineguns")
                .defineInRange("diamondMinegunDurability", 8000, 0, 32767);
        diamondMinegunInaccuracy = builder
                .comment("Configure the Inaccuracy for Mineguns")
                .defineInRange("diamondMinegunInaccuracy", 2D, 0D, 90D);
        diamondMinegunProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Mineguns")
                .defineInRange("diamondMinegunProjectileSpeed", 2.5D, 0D, 64D);
        diamondMinegunMineChance = builder
                .comment("Configure the Break Block Chance for Mineguns")
                .defineInRange("diamondMinegunMineChance", 0.5D, 0D, 1D);

        ironAssaultDamageMultiplier = builder
                .comment("Define the Damage multiplier for Assault Rifles")
                .defineInRange("ironAssaultDamageMultiplier", 0.5D, 0.1D, 5D);
        ironAssaultFireDelay = builder
                .comment("Configure the Fire delay for Assault Rifles")
                .defineInRange("ironAssaultFireDelay", 4, 0, 72000);
        ironAssaultEnchantability = builder
                .comment("Configure the Enchantability for Assault Rifles")
                .defineInRange("ironAssaultEnchantability", 14, 0, 30);
        ironAssaultDurability = builder
                .comment("Configure the Durability for Assault Rifles")
                .defineInRange("ironAssaultDurability", 24000, 0, 32767);
        ironAssaultInaccuracy = builder
                .comment("Configure the Inaccuracy for Assault Rifles")
                .defineInRange("ironAssaultInaccuracy", 3.0D, 0D, 90D);
        ironAssaultProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Assault Rifles")
                .defineInRange("ironAssaultProjectileSpeed", 10D, 0D, 64D);

        goldStreamDamageMultiplier = builder
                .comment("Define the Damage multiplier for Plasma Rifles")
                .defineInRange("goldPlasmaDamageMultiplier", 0.75D, 0.1D, 5D);
        goldStreamFireDelay = builder
                .comment("Configure the Fire delay for Plasma Rifles")
                .defineInRange("goldPlasmaFireDelay", 5, 0, 72000);
        goldStreamEnchantability = builder
                .comment("Configure the Enchantability for Plasma Rifles")
                .defineInRange("goldPlasmaEnchantability", 23, 0, 30);
        goldStreamDurability = builder
                .comment("Configure the Durability for Plasma Rifles")
                .defineInRange("goldPlasmaDurability", 8000, 0, 32767);
        goldStreamInaccuracy = builder
                .comment("Configure the Inaccuracy for Plasma Rifles")
                .defineInRange("goldPlasmaInaccuracy", 0D, 0D, 90D);
        goldStreamProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Plasma Rifles")
                .defineInRange("goldPlasmaProjectileSpeed", 2D, 0D, 64D);
        goldStreamShieldAdditional = builder
                .comment("Configure the additional damage to shields when they are in use against Plasma Rifles")
                .defineInRange("goldPlasmaShieldAdditional", 30D, -2000D, 2000D);
        builder.pop();

        builder.push("launchers");
        goldLauncherDamageMultiplier = builder
                .comment("Define the base damage to multiply against for Rocket Launchers")
                .defineInRange("goldLauncherDamageMultiplier", 1D, 0.1D, 5D);
        goldLauncherFireDelay = builder
                .comment("Configure the Fire delay for Rocket Launchers")
                .defineInRange("goldLauncherFireDelay", 32, 0, 72000);
        goldLauncherEnchantability = builder
                .comment("Configure the Enchantability for Rocket Launchers")
                .defineInRange("goldLauncherEnchantability", 23, 0, 30);
        goldLauncherDurability = builder
                .comment("Configure the Durability for Rocket Launchers")
                .defineInRange("goldLauncherDurability", 41, 0, 32767);
        goldLauncherInaccuracy = builder
                .comment("Configure the Inaccuracy for Rocket Launchers")
                .defineInRange("goldLauncherInaccuracy", 0D, 0D, 90D);
        goldLauncherProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Rocket Launchers")
                .defineInRange("goldLauncherProjectileSpeed", 2D, 0D, 64D);
        builder.pop();

        builder.push("bullet_config");
        flintBulletDamage = builder
                .comment("Configure the damage of Flint Bullets")
                .defineInRange("flintBulletDamage", 5, 1, 20);
        ironBulletDamage = builder
                .comment("Configure the damage of Iron Bullets")
                .defineInRange("ironBulletDamage", 7, 1, 20);
        blazeBulletDamage = builder
                .comment("Configure the damage of Blaze Bullets")
                .defineInRange("blazeBulletDamage", 8, 1, 20);
        hungerBulletDamage = builder
                .comment("Configure the damage of Hunger Bullets")
                .defineInRange("hungerBulletDamage", 6, 1, 20);
        builder.pop();

        builder.push("minegun_config");
        builder.comment("Also note that the first level, which mines like a hand, is of any value not listed here.");
        mineGunSecondLevel = builder
                .comment("Damage required to mine like a wooden tool")
                .defineInRange("mineGunSecondLevel", 3D, 0.5D, 40D);
        mineGunThirdLevel = builder
                .comment("Damage required to mine like a stone tool")
                .defineInRange("mineGunThirdLevel", 4D, 0.5D, 40D);
        mineGunFourthLevel = builder
                .comment("Damage required to mine like an iron tool")
                .defineInRange("mineGunFourthLevel", 5D, 0.5D, 40D);
        mineGunFifthLevel = builder
                .comment("Damage required to mine like a diamond tool")
                .defineInRange("mineGunFifthLevel", 6D, 0.5D, 40D);
        builder.pop();

        builder.push("enchantments_config");
        preservingRateIncrease = builder
                .comment("How much percentage per preserving level, represented as floating point only")
                .defineInRange("preservingRateIncrease", 0.1D, 0.1D, 1D);
        impactDamageIncrease = builder
                .comment("How much damage increase per impact level")
                .defineInRange("impactDamageIncrease", 1D, 0.1D, 20D);
        bullseyeAccuracyIncrease = builder
                .comment("How much accuracy increase per bullseye level, represented as a division + 1 of base inaccuracy")
                .defineInRange("bullseyeAccuracyIncrease", 0.5D, 0.1D, 5D);
        sleightOfHandFireRateDecrease = builder
                .comment("How much percentage fire rate increase per sleight of hand level, represented as floating point only")
                .defineInRange("sleightOfHandFireRateIncrease", 0.25D, 0.1D, 1D);
        acceleratorSpeedIncrease = builder
                .comment("How much percentage speed increase per accelerator level, represented as multiplication + 1 of base speed")
                .defineInRange("acceleratorSpeedIncrease", 0.25D, 0.1D, 1D);
        passionForBloodRateIncrease = builder
                .comment("How much percentage chance increase per passion for blood level, represented as floating point only")
                .defineInRange("passionForBloodRateIncrease", 0.1D, 0.01D, 1.0D);
        passionForBloodHealIncrease = builder
                .comment("Multiplier for how much healing passion for blood should give in relation to damage delivered")
                .defineInRange("passionForBloodHealIncrease", 0.5D, 0.1D, 5.0D);
        divisionCountIncrease = builder
                .comment("Multiplier for how many extra bullets per division level on shotguns")
                .defineInRange("divisionCountIncrease", 1, 1, 5);
        puncturingMultiplier = builder
                .comment("Multiplier for how much percent extra damage per level of puncturing")
                .defineInRange("puncturingMultiplier", 0.1D, 0.01D, 5D);
        luckyShotChance = builder
                .comment("Chance for critical hit per lucky shot level")
                .defineInRange("luckyShotChance", 0.06D, 0.01D, 5D);
        criticalDamage = builder
                .comment("Multiplier of a critical hit")
                .defineInRange("criticalDamage", 3D, 0.1D, 20D);
        oneHandInaccuracyMultiplier = builder
                .comment("Inaccuracy multiplier for when the user has one hand filled")
                .defineInRange("oneHandInaccuracyMultiplier", 2D, 0.1D, 5D);
        oneHandInaccuracyReplacement = builder
                .comment("Inaccuracy of any perfect accuracy weapons in the case of one hand being filled")
                .defineInRange("oneHandInaccuracyReplacement", 1.25D, 0D, 90D);
        builder.pop();

        spec = builder.build();
    }

}
