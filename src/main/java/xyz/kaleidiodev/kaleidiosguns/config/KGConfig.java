package xyz.kaleidiodev.kaleidiosguns.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class KGConfig {

    public static ForgeConfigSpec spec;

    //Guns
    public static ForgeConfigSpec.DoubleValue ironDamageMultiplier;
    public static ForgeConfigSpec.IntValue ironFireDelay;
    public static ForgeConfigSpec.IntValue ironEnchantability;
    public static ForgeConfigSpec.IntValue ironDurability;
    public static ForgeConfigSpec.DoubleValue ironInaccuracy;

    public static ForgeConfigSpec.DoubleValue diamondShotgunDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondShotgunFireDelay;
    public static ForgeConfigSpec.IntValue diamondShotgunEnchantability;
    public static ForgeConfigSpec.IntValue diamondShotgunDurability;
    public static ForgeConfigSpec.DoubleValue diamondShotgunInaccuracy;

    public static ForgeConfigSpec.DoubleValue diamondDoubleShotgunDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondDoubleShotgunFireDelay;
    public static ForgeConfigSpec.IntValue diamondDoubleShotgunEnchantability;
    public static ForgeConfigSpec.IntValue diamondDoubleShotgunDurability;
    public static ForgeConfigSpec.DoubleValue diamondDoubleShotgunInaccuracy;

    public static ForgeConfigSpec.DoubleValue diamondSniperDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondSniperFireDelay;
    public static ForgeConfigSpec.IntValue diamondSniperEnchantability;
    public static ForgeConfigSpec.IntValue diamondSniperDurability;
    public static ForgeConfigSpec.DoubleValue diamondSniperInaccuracy;

    public static ForgeConfigSpec.DoubleValue diamondCarbineDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondCarbineFireDelay;
    public static ForgeConfigSpec.IntValue diamondCarbineEnchantability;
    public static ForgeConfigSpec.IntValue diamondCarbineDurability;
    public static ForgeConfigSpec.DoubleValue diamondCarbineInaccuracy;

    public static ForgeConfigSpec.DoubleValue diamondSmgDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondSmgFireDelay;
    public static ForgeConfigSpec.IntValue diamondSmgEnchantability;
    public static ForgeConfigSpec.IntValue diamondSmgDurability;
    public static ForgeConfigSpec.DoubleValue diamondSmgInaccuracy;

    public static ForgeConfigSpec.DoubleValue diamondAssaultDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondAssaultFireDelay;
    public static ForgeConfigSpec.IntValue diamondAssaultEnchantability;
    public static ForgeConfigSpec.IntValue diamondAssaultDurability;
    public static ForgeConfigSpec.DoubleValue diamondAssaultInaccuracy;

    public static ForgeConfigSpec.DoubleValue diamondStreamGatlingDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondStreamGatlingFireDelay;
    public static ForgeConfigSpec.IntValue diamondStreamEnchantability;
    public static ForgeConfigSpec.IntValue diamondStreamDurability;
    public static ForgeConfigSpec.DoubleValue diamondStreamInaccuracy;

    //Bullets
    public static ForgeConfigSpec.IntValue flintBulletDamage;
    public static ForgeConfigSpec.IntValue ironBulletDamage;
    public static ForgeConfigSpec.IntValue blazeBulletDamage;
    public static ForgeConfigSpec.IntValue hungerBulletDamage;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Kaleidio's Guns Config");

        builder.push("pistols");
        ironDamageMultiplier = builder
                .comment("Define the Damage multiplier for Pistols")
                .defineInRange("ironDamageModifier", 1D, 0.5D, 1.5D);
        ironFireDelay = builder
                .comment("Configure the Fire delay for Pistols")
                .defineInRange("ironFireDelay", 12, 0, 72000);
        ironEnchantability = builder
                .comment("Configure the Enchantability for Pistols")
                .defineInRange("ironEnchantability", 14, 0, 30);
        ironDurability = builder
                .comment("Configure the Durability for Pistols")
                .defineInRange("ironDurability", 4000, 0, 32767);
        ironInaccuracy = builder
                .comment("Configure the Inaccuracy for Pistols")
                .defineInRange("ironInaccuracy", 2D, 0D, 90D);
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

        diamondDoubleShotgunDamageMultiplier = builder
                .comment("Define the Damage multiplier for Double Barrel Shotguns")
                .defineInRange("diamondDoubleShotgunDamageMultiplier", 0.5D, 0.5D, 1.5D);
        diamondDoubleShotgunFireDelay = builder
                .comment("Configure the Fire delay for Double Barrel Shotguns")
                .defineInRange("diamondDoubleShotgunFireDelay", 60, 0, 72000);
        diamondDoubleShotgunEnchantability = builder
                .comment("Configure the Enchantability for Double Barrel Shotguns")
                .defineInRange("diamondDoubleShotgunEnchantability", 23, 0, 30);
        diamondDoubleShotgunDurability = builder
                .comment("Configure the Durability for Double Barrel Shotguns")
                .defineInRange("diamondDoubleShotgunDurability", 160, 0, 32767);
        diamondDoubleShotgunInaccuracy = builder
                .comment("Configure the Inaccuracy for Double Barrel Shotguns")
                .defineInRange("diamondDoubleShotgunInaccuracy", 10D, 0D, 90D);
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

        diamondCarbineDamageMultiplier = builder
                .comment("Define the Damage multiplier for Carbines")
                .defineInRange("diamondCarbineDamageMultiplier", 1.5D, 0.5D, 2.5D);
        diamondCarbineFireDelay = builder
                .comment("Configure the Fire delay for Carbines")
                .defineInRange("diamondCarbineFireDelay", 24, 0, 72000);
        diamondCarbineEnchantability = builder
                .comment("Configure the Enchantability for Carbines")
                .defineInRange("diamondCarbineEnchantability", 14, 0, 30);
        diamondCarbineDurability = builder
                .comment("Configure the Durability for Carbines")
                .defineInRange("diamondCarbineDurability", 1334, 0, 32767);
        diamondCarbineInaccuracy = builder
                .comment("Configure the Inaccuracy for Carbines")
                .defineInRange("diamondCarbineInaccuracy", 0D, 0D, 90D);
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

        diamondAssaultDamageMultiplier = builder
                .comment("Define the Damage multiplier for Assault Rifles")
                .defineInRange("diamondAssaultDamageMultiplier", 0.75D, 0.5D, 2.5D);
        diamondAssaultFireDelay = builder
                .comment("Configure the Fire delay for Assault Rifles")
                .defineInRange("diamondAssaultFireDelay", 5, 0, 72000);
        diamondAssaultEnchantability = builder
                .comment("Configure the Enchantability for Assault Rifles")
                .defineInRange("diamondAssaultEnchantability", 14, 0, 30);
        diamondAssaultDurability = builder
                .comment("Configure the Durability for Assault Rifles")
                .defineInRange("diamondAssaultDurability", 6000, 0, 32767);
        diamondAssaultInaccuracy = builder
                .comment("Configure the Inaccuracy for Assault Rifles")
                .defineInRange("diamondAssaultInaccuracy", 2.5D, 0D, 90D);

        diamondStreamGatlingDamageMultiplier = builder
                .comment("Define the Damage multiplier for Stream Rifles")
                .defineInRange("diamondStreamGatlingDamageMultiplier", 1D, 0.5D, 2.5D);
        diamondStreamGatlingFireDelay = builder
                .comment("Configure the Fire delay for Stream Rifles")
                .defineInRange("diamondStreamGatlingFireDelay", 5, 0, 72000);
        diamondStreamEnchantability = builder
                .comment("Configure the Enchantability for Stream Rifles")
                .defineInRange("diamondStreamEnchantability", 23, 0, 30);
        diamondStreamDurability = builder
                .comment("Configure the Durability for Stream Rifles")
                .defineInRange("diamondStreamDurability", 8000, 0, 32767);
        diamondStreamInaccuracy = builder
                .comment("Configure the Inaccuracy for Stream Rifles")
                .defineInRange("diamondStreamInaccuracy", 0D, 0D, 90D);
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
