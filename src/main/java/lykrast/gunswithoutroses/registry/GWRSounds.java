package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GWRSounds {
	//"Why is that one not formatted like the others?"
	//because it wasn't on 1.18 so I just left it like that when deferredregstering it
	public static final DeferredRegister<SoundEvent> REG = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GunsWithoutRoses.MODID);
	//Gatling is just a duplicate of normal gun with different subtitle, but someone wanted it separate for resource pack purposes
	public static RegistryObject<SoundEvent> gun = initSound("item.gun.shoot"), shotgun = initSound("item.shotgun.shoot"), sniper = initSound("item.sniper.shoot"), gatling  = initSound("item.gatling.shoot");
	public static RegistryObject<SoundEvent> sniperCrit = initSound("item.sniper.crit");
	public static RegistryObject<SoundEvent> bagOpen = initSound("item.bullet_bag.open"), bagClose = initSound("item.bullet_bag.close");

	public static RegistryObject<SoundEvent> initSound(String name) {
		return REG.register(name, () -> SoundEvent.createVariableRangeEvent(GunsWithoutRoses.rl(name)));
	}
}
