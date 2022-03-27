package xyz.kaleidiodev.kaleidiosguns.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class KGConfig {

    public static ForgeConfigSpec spec;

    //Global Modifiers
    public static ForgeConfigSpec.DoubleValue fireDelayModifier;

    //Iron Guns
    public static ForgeConfigSpec.DoubleValue ironDamageMultiplier;
    public static ForgeConfigSpec.IntValue ironFireDelay;

    //Diamond Guns
    public static ForgeConfigSpec.DoubleValue diamondShotgunDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondShotgunFireDelay;

    public static ForgeConfigSpec.DoubleValue diamondDoubleShotgunDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondDoubleShotgunFireDelay;

    public static ForgeConfigSpec.DoubleValue diamondSniperDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondSniperFireDelay;

    public static ForgeConfigSpec.DoubleValue diamondCarbineDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondCarbineFireDelay;

    public static ForgeConfigSpec.DoubleValue diamondSmgDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondSmgFireDelay;

    public static ForgeConfigSpec.DoubleValue diamondAssaultDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondAssaultFireDelay;

    public static ForgeConfigSpec.DoubleValue diamondStreamGatlingDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondStreamGatlingFireDelay;

    //Bullets
    public static ForgeConfigSpec.IntValue flintBulletDamage;
    public static ForgeConfigSpec.IntValue ironBulletDamage;
    public static ForgeConfigSpec.IntValue blazeBulletDamage;
    public static ForgeConfigSpec.IntValue hungerBulletDamage;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Kaleidio's Guns Config");

        builder.push("guns_global");
        fireDelayModifier = builder
                .comment("Configure the multiplier for fire delays on weapons (don't touch!)")
                .defineInRange("fireDelayModifier", 0.2D, 0D, 1.0D);
        builder.pop();

        builder.push("pistol");
        ironDamageMultiplier = builder
                .comment("Define the Damage multiplier for Pistols")
                .defineInRange("ironDamageModifier", 1D, 0.5D, 1.5D);
        ironFireDelay = builder
                .comment("Configure the Fire delay for Pistols")
                .defineInRange("ironFireDelay", 12, 0, 48);
        builder.pop();

        builder.push("diamond_weapons");
        diamondShotgunDamageMultiplier = builder
                .comment("Define the Damage multiplier for Shotguns")
                .defineInRange("diamondShotgunDamageMultiplier", 0.5D, 0.5D, 1.5D);
        diamondShotgunFireDelay = builder
                .comment("Configure the Fire delay for Shotguns")
                .defineInRange("diamondShotgunFireDelay", 24, 0, 48);

        diamondDoubleShotgunDamageMultiplier = builder
                .comment("Define the Damage multiplier for Double Barrel Shotguns")
                .defineInRange("diamondDoubleShotgunDamageMultiplier", 0.5D, 0.5D, 1.5D);
        diamondDoubleShotgunFireDelay = builder
                .comment("Configure the Fire delay for Double Barrel Shotguns")
                .defineInRange("diamondDoubleShotgunFireDelay", 60, 0, 48);

        diamondSniperDamageMultiplier = builder
                .comment("Define the Damage multiplier for Snipers")
                .defineInRange("diamondSniperDamageMultiplier", 2.5D, 0.5D, 2.5D);
        diamondSniperFireDelay = builder
                .comment("Configure the Fire delay for Snipers")
                .defineInRange("diamondSniperFireDelay", 48, 0, 48);

        diamondCarbineDamageMultiplier = builder
                .comment("Define the Damage multiplier for Carbines")
                .defineInRange("diamondCarbineDamageMultiplier", 1.5D, 0.5D, 2.5D);
        diamondCarbineFireDelay = builder
                .comment("Configure the Fire delay for Carbines")
                .defineInRange("diamondCarbineFireDelay", 28, 0, 48);

        diamondSmgDamageMultiplier = builder
                .comment("Define the Damage multiplier for Machine Pistols")
                .defineInRange("diamondSmgDamageMultiplier", 0.75D, 0.5D, 2.5D);
        diamondSmgFireDelay = builder
                .comment("Configure the Fire delay for Machine Pistols")
                .defineInRange("diamondSmgFireDelay", 4, 0, 48);

        diamondAssaultDamageMultiplier = builder
                .comment("Define the Damage multiplier for Assault Rifles")
                .defineInRange("diamondAssaultDamageMultiplier", 0.75D, 0.5D, 2.5D);
        diamondAssaultFireDelay = builder
                .comment("Configure the Fire delay for Assault Rifles")
                .defineInRange("diamondAssaultFireDelay", 5, 0, 48);

        diamondStreamGatlingDamageMultiplier = builder
                .comment("Define the Damage multiplier for Stream Rifles")
                .defineInRange("diamondStreamGatlingDamageMultiplier", 1D, 0.5D, 2.5D);
        diamondStreamGatlingFireDelay = builder
                .comment("Configure the Fire delay for Stream Rifles")
                .defineInRange("diamondStreamGatlingFireDelay", 5, 0, 48);

        builder.push("bullet_config");
        flintBulletDamage = builder
                .comment("Configure the damage of Flint Bullets")
                .defineInRange("fireDelayModifier", 5, 1, 20);
        ironBulletDamage = builder
                .comment("Configure the damage of Iron Bullets")
                .defineInRange("fireDelayModifier", 7, 1, 20);
        blazeBulletDamage = builder
                .comment("Configure the damage of Blaze Bullets")
                .defineInRange("fireDelayModifier", 10, 1, 20);
        hungerBulletDamage = builder
                .comment("Configure the damage of Hunger Bullets")
                .defineInRange("fireDelayModifier", 6, 1, 20);
        builder.pop();

        spec = builder.build();
    }

}
