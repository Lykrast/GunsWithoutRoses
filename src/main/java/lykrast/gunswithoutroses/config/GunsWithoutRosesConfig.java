package lykrast.gunswithoutroses.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GunsWithoutRosesConfig {

    public static ForgeConfigSpec spec;

    //Global Modifiers
    public static ForgeConfigSpec.DoubleValue fireDelayModifier;

    //Iron Guns
    public static ForgeConfigSpec.DoubleValue ironDamageMultiplier;
    public static ForgeConfigSpec.IntValue ironFireDelay;

    //Gold Guns
    public static ForgeConfigSpec.DoubleValue goldDamageMultiplier;
    public static ForgeConfigSpec.IntValue goldFireDelay;

    //Diamond Guns
    public static ForgeConfigSpec.DoubleValue diamondShotgunDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondShotgunFireDelay;

    public static ForgeConfigSpec.DoubleValue diamondSniperDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondSniperFireDelay;

    public static ForgeConfigSpec.DoubleValue diamondGatlingDamageMultiplier;
    public static ForgeConfigSpec.IntValue diamondGatlingFireDelay;

    //Bullets
    public static ForgeConfigSpec.IntValue flintBulletDamage;
    public static ForgeConfigSpec.IntValue ironBulletDamage;
    public static ForgeConfigSpec.IntValue blazeBulletDamage;
    public static ForgeConfigSpec.IntValue hungerBulletDamage;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("GunsWithoutRoses Config");

        builder.push("guns_global");
        fireDelayModifier = builder
                .comment("Configure the multiplier for fire delays on weapons")
                .defineInRange("fireDelayModifier", 0.2D, 0D, 1.0D);
        builder.pop();

        builder.push("iron_gun");
        ironDamageMultiplier = builder
                .comment("Define the Damage multiplier for Iron guns")
                .defineInRange("ironDamageModifier", 1D, 0.5D, 1.5D);
        ironFireDelay = builder
                .comment("Configure the Fire delay for Iron Guns")
                .defineInRange("ironFireDelay", 16, 0, 48);
        builder.pop();

        builder.push("gold_gun");
        goldDamageMultiplier = builder
                .comment("Define the Damage multiplier for Gold guns")
                .defineInRange("goldDamageMultiplier", 1.5D, 0.5D, 1.5D);
        goldFireDelay = builder
                .comment("Configure the Fire delay for Gold Guns")
                .defineInRange("goldFireDelay", 28, 0, 48);
        builder.pop();

        builder.push("diamond_shotgun");
        diamondShotgunDamageMultiplier = builder
                .comment("Define the Damage multiplier for Diamond Shotguns")
                .defineInRange("diamondShotgunDamageMultiplier", 0.5D, 0.5D, 1.5D);
        diamondShotgunFireDelay = builder
                .comment("Configure the Fire delay for Diamond Shotguns")
                .defineInRange("diamondShotgunFireDelay", 24, 0, 48);

        diamondSniperDamageMultiplier = builder
                .comment("Define the Damage multiplier for Diamond Snipers")
                .defineInRange("diamondSniperDamageMultiplier", 2.5D, 0.5D, 2.5D);
        diamondSniperFireDelay = builder
                .comment("Configure the Fire delay for Diamond Snipers")
                .defineInRange("diamondSniperFireDelay", 48, 0, 48);

        diamondGatlingDamageMultiplier = builder
                .comment("Define the Damage multiplier for Diamond Gatlings")
                .defineInRange("diamondGatlingDamageMultiplier", 0.75D, 0.5D, 2.5D);
        diamondGatlingFireDelay = builder
                .comment("Configure the Fire delay for Diamond Gatlings")
                .defineInRange("diamondGatlingFireDelay", 4, 0, 48);
        builder.pop();

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
    }

}
