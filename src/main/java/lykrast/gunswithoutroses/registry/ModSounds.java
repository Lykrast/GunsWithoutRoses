package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GunsWithoutRoses.MODID)
public class ModSounds {
	//Items need the soundevents, so we make them before
	public static SoundEvent gun = initSound("item.gun.shoot"), shotgun = initSound("item.shotgun.shoot"), sniper = initSound("item.sniper.shoot");

	@SubscribeEvent
	public static void registerEnchantments(final RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().registerAll(gun, shotgun, sniper);
	}

	public static SoundEvent initSound(String name) {
		ResourceLocation loc = GunsWithoutRoses.rl(name);
		SoundEvent se = new SoundEvent(loc).setRegistryName(loc);
		return se;
	}
}
