package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
	//"Why is that one not formatted like the others?"
	//because it wasn't on 1.18 so I just left it like that when deferredregstering it
	public static final DeferredRegister<SoundEvent> REG = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GunsWithoutRoses.MODID);
	public static RegistryObject<SoundEvent> gun = initSound("item.gun.shoot"), shotgun = initSound("item.shotgun.shoot"), sniper = initSound("item.sniper.shoot");

	public static RegistryObject<SoundEvent> initSound(String name) {
		return REG.register(name, () -> SoundEvent.createVariableRangeEvent(GunsWithoutRoses.rl(name)));
	}
}
