package xyz.kaleidiodev.kaleidiosguns.config;

import net.minecraftforge.common.ForgeConfigSpec;

import javax.swing.text.StyledEditorKit;

public class KGConfig {

    public static ForgeConfigSpec spec;

    //mechanics
    public static ForgeConfigSpec.BooleanValue explosionsEnabled;
    public static ForgeConfigSpec.DoubleValue explosionIncreaseOnStrongerTier;
    public static ForgeConfigSpec.DoubleValue oneHandInaccuracyMultiplier;
    public static ForgeConfigSpec.DoubleValue oneHandInaccuracyReplacement;
    public static ForgeConfigSpec.DoubleValue crouchAccuracyMultiplier;
    public static ForgeConfigSpec.IntValue redstoneRadius;

    //Guns
    public static ForgeConfigSpec.DoubleValue ironPistolDamageMultiplier;
    public static ForgeConfigSpec.IntValue ironPistolFireDelay;
    public static ForgeConfigSpec.IntValue ironPistolEnchantability;
    public static ForgeConfigSpec.IntValue ironPistolDurability;
    public static ForgeConfigSpec.DoubleValue ironPistolInaccuracy;
    public static ForgeConfigSpec.DoubleValue ironPistolProjectileSpeed;

    public static ForgeConfigSpec.DoubleValue diamondRevolverDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondRevolverFireDelay;
    public static ForgeConfigSpec.IntValue diamondRevolverEnchantability;
    public static ForgeConfigSpec.IntValue diamondRevolverDurability;
    public static ForgeConfigSpec.DoubleValue diamondRevolverInaccuracy;
    public static ForgeConfigSpec.DoubleValue diamondRevolverProjectileSpeed;
    public static ForgeConfigSpec.DoubleValue diamondRevolverSpreadoutStrength;
    public static ForgeConfigSpec.IntValue diamondRevolverChamberSwitchSpeed;
    public static ForgeConfigSpec.IntValue diamondRevolverStabilityTime;

    public static ForgeConfigSpec.DoubleValue shadowRevolverDamageMultiplier;
    public static ForgeConfigSpec.IntValue shadowRevolverFireDelay;
    public static ForgeConfigSpec.IntValue shadowRevolverEnchantability;
    public static ForgeConfigSpec.IntValue shadowRevolverDurability;
    public static ForgeConfigSpec.DoubleValue shadowRevolverInaccuracy;
    public static ForgeConfigSpec.DoubleValue shadowRevolverProjectileSpeed;
    public static ForgeConfigSpec.DoubleValue shadowRevolverSpreadoutStrength;
    public static ForgeConfigSpec.IntValue shadowRevolverChamberSwitchSpeed;
    public static ForgeConfigSpec.IntValue shadowRevolverStabilityTime;
    public static ForgeConfigSpec.DoubleValue shadowRevolverShadowAdditionalMultiplier;
    public static ForgeConfigSpec.IntValue shadowRevolverLightLevelRequired;

    public static ForgeConfigSpec.DoubleValue goldSkillshotDamageMultiplier;
    public static ForgeConfigSpec.IntValue goldSkillshotFireDelay;
    public static ForgeConfigSpec.IntValue goldSkillshotEnchantability;
    public static ForgeConfigSpec.IntValue goldSkillshotDurability;
    public static ForgeConfigSpec.DoubleValue goldSkillshotInaccuracy;
    public static ForgeConfigSpec.DoubleValue goldSkillshotProjectileSpeed;
    public static ForgeConfigSpec.IntValue goldSkillshotMaxCombo;
    public static ForgeConfigSpec.DoubleValue goldSkillshotComboMultiplierPer;

    public static ForgeConfigSpec.DoubleValue emeraldBlessedDamageMultiplier;
    public static ForgeConfigSpec.IntValue emeraldBlessedFireDelay;
    public static ForgeConfigSpec.IntValue emeraldBlessedEnchantability;
    public static ForgeConfigSpec.IntValue emeraldBlessedDurability;
    public static ForgeConfigSpec.DoubleValue emeraldBlessedInaccuracy;
    public static ForgeConfigSpec.DoubleValue emeraldBlessedProjectileSpeed;
    public static ForgeConfigSpec.DoubleValue emeraldBlessedHealthMinimumRatio;
    public static ForgeConfigSpec.DoubleValue emeraldBlessedBlessingMultiplier;

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

    public static ForgeConfigSpec.DoubleValue netheriteShotgunDamageMultiplier;
    public static ForgeConfigSpec.IntValue netheriteShotgunFireDelay;
    public static ForgeConfigSpec.IntValue netheriteShotgunEnchantability;
    public static ForgeConfigSpec.IntValue netheriteShotgunDurability;
    public static ForgeConfigSpec.DoubleValue netheriteShotgunInaccuracy;
    public static ForgeConfigSpec.IntValue netheriteShotgunBulletCount;
    public static ForgeConfigSpec.DoubleValue netheriteShotgunProjectileSpeed;
    public static ForgeConfigSpec.IntValue netheriteShotgunEntityCap;
    public static ForgeConfigSpec.DoubleValue netheriteShotgunEntityHurt;
    public static ForgeConfigSpec.IntValue netheriteShotgunBulletsPerEntity;
    public static ForgeConfigSpec.DoubleValue netheriteShotgunEntityRadius;

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

    public static ForgeConfigSpec.DoubleValue diamondLauncherDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondLauncherFireDelay;
    public static ForgeConfigSpec.IntValue diamondLauncherEnchantability;
    public static ForgeConfigSpec.IntValue diamondLauncherDurability;
    public static ForgeConfigSpec.DoubleValue diamondLauncherInaccuracy;
    public static ForgeConfigSpec.DoubleValue diamondLauncherProjectileSpeed;

    public static ForgeConfigSpec.DoubleValue netheriteLauncherDamageMultiplier;
    public static ForgeConfigSpec.IntValue netheriteLauncherFireDelay;
    public static ForgeConfigSpec.IntValue netheriteLauncherEnchantability;
    public static ForgeConfigSpec.IntValue netheriteLauncherDurability;
    public static ForgeConfigSpec.DoubleValue netheriteLauncherInaccuracy;
    public static ForgeConfigSpec.DoubleValue netheriteLauncherProjectileSpeed;
    public static ForgeConfigSpec.DoubleValue netheriteLauncherEffectRadiusMultiplier;

    public static ForgeConfigSpec.DoubleValue diamondMinegunDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondMinegunFireDelay;
    public static ForgeConfigSpec.IntValue diamondMinegunEnchantability;
    public static ForgeConfigSpec.IntValue diamondMinegunDurability;
    public static ForgeConfigSpec.DoubleValue diamondMinegunInaccuracy;
    public static ForgeConfigSpec.DoubleValue diamondMinegunProjectileSpeed;
    public static ForgeConfigSpec.DoubleValue diamondMinegunMineChance;

    public static ForgeConfigSpec.DoubleValue netheriteMinegunDamageMultiplier;
    public static ForgeConfigSpec.IntValue netheriteMinegunFireDelay;
    public static ForgeConfigSpec.IntValue netheriteMinegunEnchantability;
    public static ForgeConfigSpec.IntValue netheriteMinegunDurability;
    public static ForgeConfigSpec.DoubleValue netheriteMinegunInaccuracy;
    public static ForgeConfigSpec.DoubleValue netheriteMinegunProjectileSpeed;
    public static ForgeConfigSpec.DoubleValue netheriteMinegunMineChance;
    public static ForgeConfigSpec.DoubleValue netheriteMinegunIgnitionChance;

    public static ForgeConfigSpec.DoubleValue ironVoltgunDamageMultiplier;
    public static ForgeConfigSpec.IntValue ironVoltgunFireDelay;
    public static ForgeConfigSpec.IntValue ironVoltgunEnchantability;
    public static ForgeConfigSpec.IntValue ironVoltgunDurability;
    public static ForgeConfigSpec.DoubleValue ironVoltgunInaccuracy;
    public static ForgeConfigSpec.DoubleValue ironVoltgunProjectileSpeed;
    public static ForgeConfigSpec.DoubleValue ironVoltgunMinimumDamage;
    public static ForgeConfigSpec.DoubleValue ironVoltgunMaximumDamage;

    public static ForgeConfigSpec.DoubleValue ironAssaultDamageMultiplier;
    public static ForgeConfigSpec.IntValue ironAssaultFireDelay;
    public static ForgeConfigSpec.IntValue ironAssaultEnchantability;
    public static ForgeConfigSpec.IntValue ironAssaultDurability;
    public static ForgeConfigSpec.DoubleValue ironAssaultInaccuracy;
    public static ForgeConfigSpec.DoubleValue ironAssaultProjectileSpeed;


    public static ForgeConfigSpec.DoubleValue defenderRifleDamageMultiplier;
    public static ForgeConfigSpec.IntValue defenderRifleFireDelay;
    public static ForgeConfigSpec.IntValue defenderRifleEnchantability;
    public static ForgeConfigSpec.IntValue defenderRifleDurability;
    public static ForgeConfigSpec.DoubleValue defenderRifleInaccuracy;
    public static ForgeConfigSpec.DoubleValue defenderRifleProjectileSpeed;
    public static ForgeConfigSpec.IntValue defenderRifleRange;
    public static ForgeConfigSpec.IntValue defenderRifleDelayDelta;

    public static ForgeConfigSpec.DoubleValue goldStreamDamageMultiplier;
    public static ForgeConfigSpec.IntValue goldStreamFireDelay;
    public static ForgeConfigSpec.IntValue goldStreamEnchantability;
    public static ForgeConfigSpec.IntValue goldStreamDurability;
    public static ForgeConfigSpec.DoubleValue goldStreamInaccuracy;
    public static ForgeConfigSpec.DoubleValue goldStreamProjectileSpeed;
    public static ForgeConfigSpec.DoubleValue goldStreamShieldAdditional;

    //Bullets
    public static ForgeConfigSpec.DoubleValue flintBulletDamage;
    public static ForgeConfigSpec.DoubleValue ironBulletDamage;
    public static ForgeConfigSpec.DoubleValue blazeBulletDamage;
    public static ForgeConfigSpec.DoubleValue hungerBulletDamage;

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
    public static ForgeConfigSpec.DoubleValue luckyShotChance;
    public static ForgeConfigSpec.DoubleValue criticalDamage;
    public static ForgeConfigSpec.DoubleValue frostyDistancePerLevel;
    public static ForgeConfigSpec.DoubleValue frostyMaxMultiplier;
    public static ForgeConfigSpec.DoubleValue frostyMinMultiplier;
    public static ForgeConfigSpec.DoubleValue signalMultiplier;


    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Kaleidio's Guns Config");
        builder.comment("Please note that the below options may effect the balance, as usually values cap at 100%.  higher values may not work as expected, or the curve of balance may flatten at the top.");

        builder.push("global");
        explosionsEnabled = builder
                .comment("Should explosions destroy blocks or just damage entities?  True if they should destroy blocks.")
                .define("explosionsEnabled", true);
        oneHandInaccuracyMultiplier = builder
                .comment("Inaccuracy multiplier for when the user has one hand filled")
                .defineInRange("oneHandInaccuracyMultiplier", 2.0D, 0.1D, 5D);
        oneHandInaccuracyReplacement = builder
                .comment("Inaccuracy of any perfect accuracy weapons in the case of one hand being filled")
                .defineInRange("oneHandInaccuracyReplacement", 2.0D, 0D, 90D);
        explosionIncreaseOnStrongerTier = builder
                .comment("How much larger the explosion radius would be assuming damage is stronger than a hunger bullet")
                .defineInRange("explosionIncreaseOnStrongerTier", 1D, 0D, 90D);
        crouchAccuracyMultiplier = builder
                .comment("Accuracy multiplier for when the user is crouching")
                .defineInRange("crouchAccuracyMultiplier", 3.0D, 0.1D, 5D);
        redstoneRadius = builder
                .comment("Configure the radius for a redstone block to activate redstone class gun.  WARNING, PERFORMANCE BOTTLENECKS CAN OCCUR")
                .defineInRange("redstoneRadius", 10, 0, 31);
        builder.pop();

        builder.push("pistol");
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
                .defineInRange("ironPistolDurability", 1000, 0, 32767);
        ironPistolInaccuracy = builder
                .comment("Configure the Inaccuracy for Pistols")
                .defineInRange("ironPistolInaccuracy", 2.0D, 0D, 90D);
        ironPistolProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Pistols")
                .defineInRange("ironPistolProjectileSpeed", 8D, 0D, 64D);
        builder.pop();

        builder.push("revolver");
        diamondRevolverDamageMultiplier = builder
                .comment("Define the Damage multiplier for Revolvers")
                .defineInRange("diamondRevolverDamageModifier", 1.5D, 0.1D, 5D);
        diamondRevolverFireDelay = builder
                .comment("Configure the Fire delay for Revolvers")
                .defineInRange("diamondRevolverFireDelay", 54, 0, 72000);
        diamondRevolverEnchantability = builder
                .comment("Configure the Enchantability for Revolvers")
                .defineInRange("diamondRevolverEnchantability", 10, 0, 30);
        diamondRevolverDurability = builder
                .comment("Configure the Durability for Revolvers")
                .defineInRange("diamondRevolverDurability", 347, 0, 32767);
        diamondRevolverInaccuracy = builder
                .comment("Configure the Inaccuracy for Revolvers")
                .defineInRange("diamondRevolverInaccuracy", 2.0D, 0D, 90D);
        diamondRevolverProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Revolvers")
                .defineInRange("diamondRevolverProjectileSpeed", 9D, 0D, 64D);
        diamondRevolverSpreadoutStrength = builder
                .comment("Configure the strength a Revolver increases its spread cone every time it's used too fast")
                .defineInRange("diamondRevolverSpreadoutStrength", 0.75D, 0D, 64D);
        diamondRevolverChamberSwitchSpeed = builder
                .comment("Configure a divider of base fire delay how long switching chambers takes between shots on Revolvers")
                .defineInRange("diamondRevolverChamberSwitchSpeed", 3, 1, 72000);
        diamondRevolverStabilityTime = builder
                .comment("Configure how long the gun must not be fired for spread cone to stabilize on Revolvers")
                .defineInRange("diamondRevolverStabilityTime", 28, 1, 72000);
        builder.pop();

        builder.push("revolver");
        shadowRevolverDamageMultiplier = builder
                .comment("Define the Damage multiplier for Shadow Magnums")
                .defineInRange("shadowRevolverDamageModifier", 1.3D, 0.1D, 5D);
        shadowRevolverFireDelay = builder
                .comment("Configure the Fire delay for Shadow Magnums")
                .defineInRange("shadowRevolverFireDelay", 60, 0, 72000);
        shadowRevolverEnchantability = builder
                .comment("Configure the Enchantability for Shadow Magnums")
                .defineInRange("shadowRevolverEnchantability", 15, 0, 30);
        shadowRevolverDurability = builder
                .comment("Configure the Durability for Shadow Magnums")
                .defineInRange("shadowRevolverDurability", 461, 0, 32767);
        shadowRevolverInaccuracy = builder
                .comment("Configure the Inaccuracy for Shadow Magnums")
                .defineInRange("shadowRevolverInaccuracy", 1.5D, 0D, 90D);
        shadowRevolverProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Shadow Magnums")
                .defineInRange("shadowRevolverProjectileSpeed", 10D, 0D, 64D);
        shadowRevolverSpreadoutStrength = builder
                .comment("Configure the strength a Shadow Magnums increases its spread cone every time it's used too fast")
                .defineInRange("shadowRevolverSpreadoutStrength", 0.5D, 0D, 64D);
        shadowRevolverChamberSwitchSpeed = builder
                .comment("Configure a divider of base fire delay how long switching chambers takes between shots on Shadow Magnums")
                .defineInRange("shadowRevolverChamberSwitchSpeed", 3, 1, 72000);
        shadowRevolverStabilityTime = builder
                .comment("Configure how long the gun must not be fired for spread cone to stabilize on Shadow Magnums")
                .defineInRange("shadowRevolverStabilityTime", 30, 1, 72000);
        shadowRevolverShadowAdditionalMultiplier = builder
                .comment("Define the additional damage multiplier for Shadow Magnums when the player is standing in the dark.  Is additive to base multiplier.")
                .defineInRange("shadowRevolverShadowAdditionalMultiplier", 0.7D, 0.1D, 5D);
        shadowRevolverLightLevelRequired = builder
                .comment("Configure what light level the block the player is standing in has to be or less to multiply damage on Shadow Magnums.")
                .defineInRange("shadowRevolverLightLevelRequired", 3, 0, 15);
        builder.pop();

        builder.push("skillshot pistol");
        goldSkillshotDamageMultiplier = builder
                .comment("Define the Damage multiplier for Skillshot Pistols")
                .defineInRange("goldSkillshotDamageModifier", 1.0D, 0.1D, 5D);
        goldSkillshotFireDelay = builder
                .comment("Configure the Fire delay for Skillshot Pistols")
                .defineInRange("goldSkillshotFireDelay", 20, 0, 72000);
        goldSkillshotEnchantability = builder
                .comment("Configure the Enchantability for Skillshot Pistols")
                .defineInRange("goldSkillshotEnchantability", 23, 0, 30);
        goldSkillshotDurability = builder
                .comment("Configure the Durability for Skillshot Pistols")
                .defineInRange("goldSkillshotDurability", 500, 0, 32767);
        goldSkillshotInaccuracy = builder
                .comment("Configure the Inaccuracy for Skillshot Pistols")
                .defineInRange("goldSkillshotInaccuracy", 1.5D, 0D, 90D);
        goldSkillshotProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Skillshot Pistols")
                .defineInRange("goldSkillshotProjectileSpeed", 6D, 0D, 64D);
        goldSkillshotMaxCombo = builder
                .comment("Configure the maximum combo on Skillshot Pistols")
                .defineInRange("goldSkillshotMaxCombo", 5, 2, 255);
        goldSkillshotComboMultiplierPer = builder
                .comment("Configure the addition to damage multiplier per combo for Skillshot Pistols")
                .defineInRange("goldSkillshotComboMultplierPer", 0.2D, 0D, 20D);
        builder.pop();

        builder.push("blessed pistol");
        emeraldBlessedDamageMultiplier = builder
                .comment("Define the Damage multiplier for Blessed Pistols")
                .defineInRange("emeraldBlessedDamageModifier", 1D, 0.1D, 5D);
        emeraldBlessedFireDelay = builder
                .comment("Configure the Fire delay for Blessed Pistols")
                .defineInRange("emeraldBlessedFireDelay", 14, 0, 72000);
        emeraldBlessedEnchantability = builder
                .comment("Configure the Enchantability for Blessed Pistols")
                .defineInRange("emeraldBlessedEnchantability", 18, 0, 30);
        emeraldBlessedDurability = builder
                .comment("Configure the Durability for Blessed Pistols")
                .defineInRange("emeraldBlessedDurability", 714, 0, 32767);
        emeraldBlessedInaccuracy = builder
                .comment("Configure the Inaccuracy for Blessed Pistols")
                .defineInRange("emeraldBlessedInaccuracy", 2.0D, 0D, 90D);
        emeraldBlessedProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Blessed Pistols")
                .defineInRange("emeraldBlessedProjectileSpeed", 7D, 0D, 64D);
        emeraldBlessedHealthMinimumRatio = builder
                .comment("Configure the Minimum Ratio before blessing takes effect for Blessed Pistols")
                .defineInRange("emeraldBlessedHealthMinimumRatio", 0.3D, 0.05D, 1.0D);
        emeraldBlessedBlessingMultiplier = builder
                .comment("Configure the blessing's damage multiplier for Blessed Pistols")
                .defineInRange("emeraldBlessedBlessingMultiplier", 1.5D, 0D, 20D);
        builder.pop();

        builder.push("shotgun");
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
                .defineInRange("diamondShotgunDurability", 250, 0, 32767);
        diamondShotgunInaccuracy = builder
                .comment("Configure the Inaccuracy for Shotguns")
                .defineInRange("diamondShotgunInaccuracy", 4D, 0D, 90D);
        diamondShotgunBulletCount = builder
                .comment("Configure the amount of Bullets at once for Shotguns")
                .defineInRange("diamondShotgunBulletCount", 5, 0, 50);
        diamondShotgunProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Shotguns")
                .defineInRange("diamondShotgunProjectileSpeed", 8D, 0D, 64D);
        builder.pop();

        builder.push("double barrel shotgun");
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
                .defineInRange("goldDoubleShotgunDurability", 100, 0, 32767);
        goldDoubleShotgunInaccuracy = builder
                .comment("Configure the Inaccuracy for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunInaccuracy", 5D, 0D, 90D);
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

        builder.push("vampire shotgun");
        netheriteShotgunDamageMultiplier = builder
                .comment("Define the Damage multiplier for Vampire Shotguns")
                .defineInRange("netheriteShotgunDamageMultiplier", 0.5D, 0.1D, 5D);
        netheriteShotgunFireDelay = builder
                .comment("Configure the Fire delay for Vampire Shotguns")
                .defineInRange("netheriteShotgunFireDelay", 30, 0, 72000);
        netheriteShotgunEnchantability = builder
                .comment("Configure the Enchantability for Vampire Shotguns")
                .defineInRange("netheriteShotgunEnchantability", 15, 0, 30);
        netheriteShotgunDurability = builder
                .comment("Configure the Durability for Vampire Shotguns")
                .defineInRange("netheriteShotgunDurability", 160, 0, 32767);
        netheriteShotgunInaccuracy = builder
                .comment("Configure the Inaccuracy for Vampire Shotguns")
                .defineInRange("netheriteShotgunInaccuracy", 4D, 0D, 90D);
        netheriteShotgunBulletCount = builder
                .comment("Configure the amount of Bullets at once for Vampire Shotguns")
                .defineInRange("netheriteShotgunBulletCount", 4, 0, 50);
        netheriteShotgunProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Vampire Shotguns")
                .defineInRange("netheriteShotgunProjectileSpeed", 8D, 0D, 64D);
        netheriteShotgunEntityCap = builder
                .comment("Configure the amount of entities at once for Vampire Shotguns")
                .defineInRange("netheriteShotgunEntityCap", 5, 1, 25);
        netheriteShotgunEntityHurt = builder
                .comment("Configure the amount of hearts stolen per entity for Vampire Shotguns")
                .defineInRange("netheriteShotgunEntityHurt", 2D, 0D, 1000D);
        netheriteShotgunBulletsPerEntity = builder
                .comment("Configure the amount of bullets per entity sacrifice for Vampire Shotguns")
                .defineInRange("netheriteShotgunBulletsPerEntity", 1, 1, 5);
        netheriteShotgunEntityRadius = builder
                .comment("Configure the radius for entities to steal hearts from for Vampire Shotguns")
                .defineInRange("netheriteShotgunEntityRadius", 8D, 1D, 8D);
        builder.pop();

        builder.push("sniper rifle");
        diamondSniperDamageMultiplier = builder
                .comment("Define the Damage multiplier for Snipers")
                .defineInRange("diamondSniperDamageMultiplier", 2D, 0.1D, 5D);
        diamondSniperFireDelay = builder
                .comment("Configure the Fire delay for Snipers")
                .defineInRange("diamondSniperFireDelay", 32, 0, 72000);
        diamondSniperEnchantability = builder
                .comment("Configure the Enchantability for Snipers")
                .defineInRange("diamondSniperEnchantability", 10, 0, 30);
        diamondSniperDurability = builder
                .comment("Configure the Durability for Snipers")
                .defineInRange("diamondSniperDurability", 171, 0, 32767);
        diamondSniperInaccuracy = builder
                .comment("Configure the Inaccuracy for Snipers")
                .defineInRange("diamondSniperInaccuracy", 0D, 0D, 90D);
        diamondSniperProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Snipers")
                .defineInRange("diamondSniperProjectileSpeed", 16D, 0D, 64D);
        builder.pop();

        builder.push("carbine");
        ironCarbineDamageMultiplier = builder
                .comment("Define the Damage multiplier for Carbines")
                .defineInRange("ironCarbineDamageMultiplier", 1.5D, 0.5D, 5D);
        ironCarbineFireDelay = builder
                .comment("Configure the Fire delay for Carbines")
                .defineInRange("ironCarbineFireDelay", 18, 0, 72000);
        ironCarbineEnchantability = builder
                .comment("Configure the Enchantability for Carbines")
                .defineInRange("ironCarbineEnchantability", 14, 0, 30);
        ironCarbineDurability = builder
                .comment("Configure the Durability for Carbines")
                .defineInRange("ironCarbineDurability", 444, 0, 32767);
        ironCarbineInaccuracy = builder
                .comment("Configure the Inaccuracy for Carbines")
                .defineInRange("ironCarbineInaccuracy", 0D, 0D, 90D);
        ironCarbineProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Carbines")
                .defineInRange("ironCarbineProjectileSpeed", 12D, 0D, 64D);
        builder.pop();

        builder.push("minegun");
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
                .defineInRange("diamondMinegunDurability", 2000, 0, 32767);
        diamondMinegunInaccuracy = builder
                .comment("Configure the Inaccuracy for Mineguns")
                .defineInRange("diamondMinegunInaccuracy", 3D, 0D, 90D);
        diamondMinegunProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Mineguns")
                .defineInRange("diamondMinegunProjectileSpeed", 2.5D, 0D, 64D);
        diamondMinegunMineChance = builder
                .comment("Configure the Break Block Chance for Mineguns")
                .defineInRange("diamondMinegunMineChance", 0.5D, 0D, 1D);
        builder.pop();

        builder.push("corruption gun");
        netheriteMinegunDamageMultiplier = builder
                .comment("Define the Damage multiplier for Corruption Gun")
                .defineInRange("netheriteMinegunDamageMultiplier", 0.75D, 0.1D, 5D);
        netheriteMinegunFireDelay = builder
                .comment("Configure the Fire delay for Corruption Gun")
                .defineInRange("netheriteMinegunFireDelay", 7, 0, 72000);
        netheriteMinegunEnchantability = builder
                .comment("Configure the Enchantability for Corruption Gun")
                .defineInRange("netheriteMinegunEnchantability", 10, 0, 30);
        netheriteMinegunDurability = builder
                .comment("Configure the Durability for Corruption Gun")
                .defineInRange("netheriteMinegunDurability",  2286, 0, 32767);
        netheriteMinegunInaccuracy = builder
                .comment("Configure the Inaccuracy for Corruption Gun")
                .defineInRange("netheriteMinegunInaccuracy", 2D, 0D, 90D);
        netheriteMinegunProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Corruption Gun")
                .defineInRange("netheriteMinegunProjectileSpeed", 3D, 0D, 64D);
        netheriteMinegunMineChance = builder
                .comment("Configure the Break Block Chance for Corruption Gun")
                .defineInRange("netheriteMinegunMineChance", 0.75D, 0D, 1D);
        netheriteMinegunIgnitionChance = builder
                .comment("Configure the Fire Set Chance for Corruption Guns when it is a blaze bullet in use")
                .defineInRange("netheriteMinegunIgnitionChance", 0.25D, 0D, 1D);
        builder.pop();

        builder.push("voltgun");
        ironVoltgunDamageMultiplier = builder
                .comment("Define the Damage multiplier for Voltguns")
                .defineInRange("ironVoltgunDamageMultiplier", 1D, 0.1D, 5D);
        ironVoltgunFireDelay = builder
                .comment("Configure the Fire delay for Voltguns")
                .defineInRange("ironVoltgunFireDelay", 15, 0, 72000);
        ironVoltgunEnchantability = builder
                .comment("Configure the Enchantability for Voltguns")
                .defineInRange("ironVoltgunEnchantability", 14, 0, 30);
        ironVoltgunDurability = builder
                .comment("Configure the Durability for Voltguns")
                .defineInRange("ironVoltgunDurability", 744, 0, 32767);
        ironVoltgunInaccuracy = builder
                .comment("Configure the Inaccuracy for Voltguns")
                .defineInRange("ironVoltgunInaccuracy", 2.5D, 0D, 90D);
        ironVoltgunProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Voltguns")
                .defineInRange("ironVoltgunProjectileSpeed", 6D, 0D, 64D);
        ironVoltgunMinimumDamage = builder
                .comment("Configure the Minimum damage increase for Voltguns")
                .defineInRange("ironVoltgunMinimumDamage", 0.75D, 0D, 5D);
        ironVoltgunMaximumDamage = builder
                .comment("Configure the Maximum damage increase for Voltguns")
                .defineInRange("ironVoltgunMaximumDamage", 1.5D, 0D, 5D);
        builder.pop();

        builder.push("assault rifle");
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
                .defineInRange("ironAssaultDurability", 6000, 0, 32767);
        ironAssaultInaccuracy = builder
                .comment("Configure the Inaccuracy for Assault Rifles")
                .defineInRange("ironAssaultInaccuracy", 3.0D, 0D, 90D);
        ironAssaultProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Assault Rifles")
                .defineInRange("ironAssaultProjectileSpeed", 10D, 0D, 64D);
        builder.pop();

        builder.push("plasma rifle");
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
                .defineInRange("goldPlasmaDurability", 3200, 0, 32767);
        goldStreamInaccuracy = builder
                .comment("Configure the Inaccuracy for Plasma Rifles")
                .defineInRange("goldPlasmaInaccuracy", 0D, 0D, 90D);
        goldStreamProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Plasma Rifles")
                .defineInRange("goldPlasmaProjectileSpeed", 2D, 0D, 64D);
        goldStreamShieldAdditional = builder
                .comment("Configure the chance for shields to be disabled against Plasma Rifles")
                .defineInRange("goldPlasmaShieldAdditional", 0.25D, 0.05D, 1D);
        builder.pop();

        builder.push("defenders carbine");
        defenderRifleDamageMultiplier = builder
                .comment("Define the Damage multiplier for Defender Rifles")
                .defineInRange("defenderRifleDamageMultiplier", 0.6D, 0.1D, 5D);
        defenderRifleFireDelay = builder
                .comment("Configure the Fire delay for Defender Rifles")
                .defineInRange("defenderRifleFireDelay", 5, 0, 72000);
        defenderRifleEnchantability = builder
                .comment("Configure the Enchantability for Defender Rifles")
                .defineInRange("defenderRifleEnchantability", 18, 0, 30);
        defenderRifleDurability = builder
                .comment("Configure the Durability for Defender Rifles")
                .defineInRange("defenderRifleDurability", 4500, 0, 32767);
        defenderRifleInaccuracy = builder
                .comment("Configure the Inaccuracy for Defender Rifles")
                .defineInRange("defenderRifleInaccuracy", 2.0D, 0D, 90D);
        defenderRifleProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Defender Rifles")
                .defineInRange("defenderRifleProjectileSpeed", 8D, 0D, 64D);
        defenderRifleRange = builder
                .comment("Configure the range for a Tile Entity for Defender Rifles.  WARNING, PERFORMANCE BOTTLENECKS CAN OCCUR")
                .defineInRange("defenderRifleRange", 7, 0, 32);
        defenderRifleDelayDelta = builder
                .comment("Configure the subtractor for Fire Rate when next to a Tile Entity for Defender Rifles")
                .defineInRange("defenderRifleDelayDelta", 1, 0, 72000);
        builder.pop();

        builder.push("rocket launcher");
        diamondLauncherDamageMultiplier = builder
                .comment("Define the base damage to multiply against for Rocket Launchers")
                .defineInRange("diamondLauncherDamageMultiplier", 1.25D, 0.1D, 5D);
        diamondLauncherFireDelay = builder
                .comment("Configure the Fire delay for Rocket Launchers")
                .defineInRange("diamondLauncherFireDelay", 64, 0, 72000);
        diamondLauncherEnchantability = builder
                .comment("Configure the Enchantability for Rocket Launchers")
                .defineInRange("diamondLauncherEnchantability", 10, 0, 30);
        diamondLauncherDurability = builder
                .comment("Configure the Durability for Rocket Launchers")
                .defineInRange("diamondLauncherDurability", 37, 0, 32767);
        diamondLauncherInaccuracy = builder
                .comment("Configure the Inaccuracy for Rocket Launchers")
                .defineInRange("diamondLauncherInaccuracy", 0D, 0D, 90D);
        diamondLauncherProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Rocket Launchers")
                .defineInRange("diamondLauncherProjectileSpeed", 2D, 0D, 64D);
        builder.pop();

        builder.push("wither cannon");
        netheriteLauncherDamageMultiplier = builder
                .comment("Define the base damage to multiply against for Wither Launchers")
                .defineInRange("witherLauncherDamageMultiplier", 1D, 0.1D, 5D);
        netheriteLauncherFireDelay = builder
                .comment("Configure the Fire delay for Wither Launchers")
                .defineInRange("witherLauncherFireDelay", 60, 0, 72000);
        netheriteLauncherEnchantability = builder
                .comment("Configure the Enchantability for Wither Launchers")
                .defineInRange("witherLauncherEnchantability", 15, 0, 30);
        netheriteLauncherDurability = builder
                .comment("Configure the Durability for Wither Launchers")
                .defineInRange("witherLauncherDurability", 37, 0, 32767);
        netheriteLauncherInaccuracy = builder
                .comment("Configure the Inaccuracy for Wither Launchers")
                .defineInRange("witherLauncherInaccuracy", 0D, 0D, 90D);
        netheriteLauncherProjectileSpeed = builder
                .comment("Configure the Projectile Speed for Wither Launchers")
                .defineInRange("witherLauncherProjectileSpeed", 2D, 0D, 64D);
        netheriteLauncherEffectRadiusMultiplier = builder
                .comment("Multiplier of how much larger than the explosion radius does the wither effect apply")
                .defineInRange("witherLauncherEffectRadiusMultiplier", 2D, 0D, 64D);
        builder.pop();

        builder.push("bullet_config");
        flintBulletDamage = builder
                .comment("Configure the damage of Flint Bullets")
                .defineInRange("flintBulletDamage", 5D, 1D, 20D);
        ironBulletDamage = builder
                .comment("Configure the damage of Iron Bullets")
                .defineInRange("ironBulletDamage", 6D, 1D, 20D);
        blazeBulletDamage = builder
                .comment("Configure the damage of Blaze Bullets")
                .defineInRange("blazeBulletDamage", 7D, 1D, 20D);
        hungerBulletDamage = builder
                .comment("Configure the damage of Hunger Bullets")
                .defineInRange("hungerBulletDamage", 8D, 1D, 20D);
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

        builder.push("preserving enchantment");
        preservingRateIncrease = builder
                .comment("How much percentage per preserving level, represented as floating point only")
                .defineInRange("preservingRateIncrease", 0.2D, 0.1D, 1D);
        builder.pop();

        builder.push("impact enchantment");
        impactDamageIncrease = builder
                .comment("How much damage increase per impact level")
                .defineInRange("impactDamageIncrease", 1D, 0.1D, 20D);
        builder.pop();

        builder.push("bullseye enchantment");
        bullseyeAccuracyIncrease = builder
                .comment("How much accuracy increase per bullseye level, represented as a division + 1 of base inaccuracy")
                .defineInRange("bullseyeAccuracyIncrease", 0.5D, 0.1D, 5D);
        builder.pop();

        builder.push("sleight of hand enchantment");
        sleightOfHandFireRateDecrease = builder
                .comment("How much percentage fire rate increase per sleight of hand level, represented as floating point only")
                .defineInRange("sleightOfHandFireRateIncrease", 0.25D, 0.1D, 1D);
        builder.pop();

        builder.push("accelerator enchantment");
        acceleratorSpeedIncrease = builder
                .comment("How much percentage speed increase per accelerator level, represented as multiplication + 1 of base speed")
                .defineInRange("acceleratorSpeedIncrease", 0.25D, 0.1D, 1D);
        builder.pop();

        builder.push("passion for blood enchantment");
        passionForBloodRateIncrease = builder
                .comment("How much percentage chance increase per passion for blood level, represented as floating point only")
                .defineInRange("passionForBloodRateIncrease", 0.2D, 0.01D, 1.0D);
        passionForBloodHealIncrease = builder
                .comment("Multiplier for how much healing passion for blood should give in relation to damage delivered")
                .defineInRange("passionForBloodHealIncrease", 0.1D, 0.1D, 5.0D);
        builder.pop();

        builder.push("division enchantment");
        divisionCountIncrease = builder
                .comment("Multiplier for how many extra bullets per division level on shotguns")
                .defineInRange("divisionCountIncrease", 1, 1, 5);
        builder.pop();

        builder.push("lucky shot enchantment");
        luckyShotChance = builder
                .comment("Chance for critical hit per lucky shot level")
                .defineInRange("luckyShotChance", 0.075D, 0.01D, 5D);
        criticalDamage = builder
                .comment("Multiplier of a critical hit's damage")
                .defineInRange("criticalDamage", 3D, 0.1D, 20D);
        builder.pop();

        builder.push("frost shard enchantment");
        frostyMaxMultiplier = builder
                .comment("Maximum damage of a frosty bullet")
                .defineInRange("frostyMaxMultiplier", 2.5D, 0.1D, 20D);
        frostyMinMultiplier = builder
                .comment("Minimum damage of a frosty bullet")
                .defineInRange("frostyMinMultiplier", 0.5D, 0.1D, 20D);
        frostyDistancePerLevel = builder
                .comment("Block distance from origin until a frosty bullet is at minimum multiplier")
                .defineInRange("frostyDistancePerLevel", 20D, 0D, 128D);
        builder.pop();

        builder.push("signal boost enchantment");
        signalMultiplier = builder
                .comment("Additive multiplier per level of signal boost")
                .defineInRange("signalMultiplier", 0.5D, 0.1D, 20D);
        builder.pop();

        spec = builder.build();
    }

}
