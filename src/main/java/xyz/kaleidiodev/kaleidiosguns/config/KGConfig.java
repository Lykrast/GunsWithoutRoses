package xyz.kaleidiodev.kaleidiosguns.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class KGConfig {

    public static ForgeConfigSpec spec;

    //Guns
    public static ForgeConfigSpec.DoubleValue ironPistolDamageMultiplier;
    public static ForgeConfigSpec.IntValue ironPistolFireDelay;
    public static ForgeConfigSpec.IntValue ironPistolEnchantability;
    public static ForgeConfigSpec.IntValue ironPistolDurability;
    public static ForgeConfigSpec.DoubleValue ironPistolInaccuracy;

    public static ForgeConfigSpec.DoubleValue diamondShotgunDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondShotgunFireDelay;
    public static ForgeConfigSpec.IntValue diamondShotgunEnchantability;
    public static ForgeConfigSpec.IntValue diamondShotgunDurability;
    public static ForgeConfigSpec.DoubleValue diamondShotgunInaccuracy;
    public static ForgeConfigSpec.IntValue diamondShotgunBulletCount;

    public static ForgeConfigSpec.DoubleValue goldDoubleShotgunDamageMultiplier;
    public static ForgeConfigSpec.IntValue goldDoubleShotgunFireDelay;
    public static ForgeConfigSpec.IntValue goldDoubleShotgunEnchantability;
    public static ForgeConfigSpec.IntValue goldDoubleShotgunDurability;
    public static ForgeConfigSpec.DoubleValue goldDoubleShotgunInaccuracy;
    public static ForgeConfigSpec.IntValue goldDoubleShotgunBulletCount;

    public static ForgeConfigSpec.DoubleValue diamondSniperDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondSniperFireDelay;
    public static ForgeConfigSpec.IntValue diamondSniperEnchantability;
    public static ForgeConfigSpec.IntValue diamondSniperDurability;
    public static ForgeConfigSpec.DoubleValue diamondSniperInaccuracy;

    public static ForgeConfigSpec.DoubleValue ironCarbineDamageMultiplier;
    public static ForgeConfigSpec.IntValue ironCarbineFireDelay;
    public static ForgeConfigSpec.IntValue ironCarbineEnchantability;
    public static ForgeConfigSpec.IntValue ironCarbineDurability;
    public static ForgeConfigSpec.DoubleValue ironCarbineInaccuracy;

    public static ForgeConfigSpec.DoubleValue diamondSmgDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondSmgFireDelay;
    public static ForgeConfigSpec.IntValue diamondSmgEnchantability;
    public static ForgeConfigSpec.IntValue diamondSmgDurability;
    public static ForgeConfigSpec.DoubleValue diamondSmgInaccuracy;

    public static ForgeConfigSpec.DoubleValue ironAssaultDamageMultiplier;
    public static ForgeConfigSpec.IntValue ironAssaultFireDelay;
    public static ForgeConfigSpec.IntValue ironAssaultEnchantability;
    public static ForgeConfigSpec.IntValue ironAssaultDurability;
    public static ForgeConfigSpec.DoubleValue ironAssaultInaccuracy;

    public static ForgeConfigSpec.DoubleValue goldStreamDamageMultiplier;
    public static ForgeConfigSpec.IntValue goldStreamFireDelay;
    public static ForgeConfigSpec.IntValue goldStreamEnchantability;
    public static ForgeConfigSpec.IntValue goldStreamDurability;
    public static ForgeConfigSpec.DoubleValue goldStreamInaccuracy;

    //Bullets
    public static ForgeConfigSpec.IntValue flintBulletDamage;
    public static ForgeConfigSpec.IntValue ironBulletDamage;
    public static ForgeConfigSpec.IntValue blazeBulletDamage;
    public static ForgeConfigSpec.IntValue hungerBulletDamage;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Kaleidio's Guns Config");

        builder.push("pistols");
        ironPistolDamageMultiplier = builder
                .comment("Define the Damage multiplier for Pistols")
                .defineInRange("ironPistolDamageModifier", 1D, 0.5D, 1.5D);
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
                .defineInRange("ironPistolInaccuracy", 2D, 0D, 90D);
        builder.pop();

        builder.push("shotguns");
        diamondShotgunDamageMultiplier = builder
                .comment("Define the Damage multiplier for Shotguns")
                .defineInRange("diamondShotgunDamageMultiplier", 0.5D, 0.5D, 1.5D);
        diamondShotgunFireDelay = builder
                .comment("Configure the Fire delay for Shotguns")
                .defineInRange("diamondShotgunFireDelay", 24, 0, 72000);
        diamondShotgunEnchantability = builder
                .comment("Configure the Enchantability for Shotguns")
                .defineInRange("diamondShotgunEnchantability", 10, 0, 30);
        diamondShotgunDurability = builder
                .comment("Configure the Durability for Shotguns")
                .defineInRange("diamondShotgunDurability", 800, 0, 32767);
        diamondShotgunInaccuracy = builder
                .comment("Configure the Inaccuracy for Shotguns")
                .defineInRange("diamondShotgunInaccuracy", 5D, 0D, 90D);
        diamondShotgunBulletCount = builder
                .comment("Configure the amount of Bullets at once for Shotguns")
                .defineInRange("diamondShotgunInaccuracy", 5, 0, 64);

        goldDoubleShotgunDamageMultiplier = builder
                .comment("Define the Damage multiplier for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunDamageMultiplier", 0.5D, 0.5D, 1.5D);
        goldDoubleShotgunFireDelay = builder
                .comment("Configure the Fire delay for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunFireDelay", 60, 0, 72000);
        goldDoubleShotgunEnchantability = builder
                .comment("Configure the Enchantability for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunEnchantability", 23, 0, 30);
        goldDoubleShotgunDurability = builder
                .comment("Configure the Durability for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunDurability", 160, 0, 32767);
        goldDoubleShotgunInaccuracy = builder
                .comment("Configure the Inaccuracy for Double Barrel Shotguns")
                .defineInRange("goldDoubleShotgunInaccuracy", 10D, 0D, 90D);
        diamondShotgunBulletCount = builder
                .comment("Configure the amount of Bullets at once for Double Barrel Shotguns")
                .defineInRange("diamondShotgunInaccuracy", 10, 0, 64);
        builder.pop();

        builder.push("rifles");
        diamondSniperDamageMultiplier = builder
                .comment("Define the Damage multiplier for Snipers")
                .defineInRange("diamondSniperDamageMultiplier", 2.5D, 0.5D, 2.5D);
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

        ironCarbineDamageMultiplier = builder
                .comment("Define the Damage multiplier for Carbines")
                .defineInRange("ironCarbineDamageMultiplier", 1.5D, 0.5D, 2.5D);
        ironCarbineFireDelay = builder
                .comment("Configure the Fire delay for Carbines")
                .defineInRange("ironCarbineFireDelay", 24, 0, 72000);
        ironCarbineEnchantability = builder
                .comment("Configure the Enchantability for Carbines")
                .defineInRange("ironCarbineEnchantability", 14, 0, 30);
        ironCarbineDurability = builder
                .comment("Configure the Durability for Carbines")
                .defineInRange("ironCarbineDurability", 1334, 0, 32767);
        ironCarbineInaccuracy = builder
                .comment("Configure the Inaccuracy for Carbines")
                .defineInRange("ironCarbineInaccuracy", 0D, 0D, 90D);
        builder.pop();

        builder.push("automatics");
        diamondSmgDamageMultiplier = builder
                .comment("Define the Damage multiplier for Machine Pistols")
                .defineInRange("diamondSmgDamageMultiplier", 0.75D, 0.5D, 2.5D);
        diamondSmgFireDelay = builder
                .comment("Configure the Fire delay for Machine Pistols")
                .defineInRange("diamondSmgFireDelay", 4, 0, 72000);
        diamondSmgEnchantability = builder
                .comment("Configure the Enchantability for Smgs")
                .defineInRange("diamondSmgEnchantability", 10, 0, 30);
        diamondSmgDurability = builder
                .comment("Configure the Durability for Smgs")
                .defineInRange("diamondSmgDurability", 16000, 0, 32767);
        diamondSmgInaccuracy = builder
                .comment("Configure the Inaccuracy for Smgs")
                .defineInRange("diamondSmgInaccuracy", 5D, 0D, 90D);

        ironAssaultDamageMultiplier = builder
                .comment("Define the Damage multiplier for Assault Rifles")
                .defineInRange("ironAssaultDamageMultiplier", 0.75D, 0.5D, 2.5D);
        ironAssaultFireDelay = builder
                .comment("Configure the Fire delay for Assault Rifles")
                .defineInRange("ironAssaultFireDelay", 5, 0, 72000);
        ironAssaultEnchantability = builder
                .comment("Configure the Enchantability for Assault Rifles")
                .defineInRange("ironAssaultEnchantability", 14, 0, 30);
        ironAssaultDurability = builder
                .comment("Configure the Durability for Assault Rifles")
                .defineInRange("ironAssaultDurability", 6000, 0, 32767);
        ironAssaultInaccuracy = builder
                .comment("Configure the Inaccuracy for Assault Rifles")
                .defineInRange("ironAssaultInaccuracy", 2.5D, 0D, 90D);

        goldStreamDamageMultiplier = builder
                .comment("Define the Damage multiplier for Stream Rifles")
                .defineInRange("goldStreamDamageMultiplier", 1D, 0.5D, 2.5D);
        goldStreamFireDelay = builder
                .comment("Configure the Fire delay for Stream Rifles")
                .defineInRange("goldStreamFireDelay", 5, 0, 72000);
        goldStreamEnchantability = builder
                .comment("Configure the Enchantability for Stream Rifles")
                .defineInRange("goldStreamEnchantability", 23, 0, 30);
        goldStreamDurability = builder
                .comment("Configure the Durability for Stream Rifles")
                .defineInRange("goldStreamDurability", 8000, 0, 32767);
        goldStreamInaccuracy = builder
                .comment("Configure the Inaccuracy for Stream Rifles")
                .defineInRange("goldStreamInaccuracy", 0D, 0D, 90D);
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

        spec = builder.build();
    }

}
