package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public class GWRDamage {
	//Copying Alex's caves for this https://github.com/AlexModGuy/AlexsCaves/blob/main/src/main/java/com/github/alexmodguy/alexscaves/server/misc/ACDamageTypes.java
	public static final ResourceKey<DamageType> GUN = ResourceKey.create(Registries.DAMAGE_TYPE, GunsWithoutRoses.rl("gwrgun"));
	
	public static DamageSource gunDamage(RegistryAccess ra, Entity bullet) {
		return new DamageSource(ra.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(GUN), bullet);
	}
	public static DamageSource gunDamage(RegistryAccess ra, Entity bullet, Entity shooter) {
		return new DamageSource(ra.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(GUN), bullet, shooter);
	}

}
