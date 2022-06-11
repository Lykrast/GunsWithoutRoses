package xyz.kaleidiodev.kaleidiosguns.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.kaleidiodev.kaleidiosguns.KaleidiosGuns;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = KaleidiosGuns.MODID)
public class ModSounds {

	//Items need the soundevents, so we make them before
	public static SoundEvent
			gun = initSound("item.gun.shoot"),
			pistol = initSound("item.pistol.shoot"),
			smg = initSound("item.smg.shoot"),
			double_shotgun = initSound("item.double_shotgun.shoot"),
			plasma_rifle = initSound("item.plasma_rifle.shoot"),
			carbine = initSound("item.carbine.shoot"),
			shotgun = initSound("item.shotgun.shoot"),
			sniper = initSound("item.sniper.shoot"),
			revolver = initSound("item.revolver.shoot"),
			impact = initSound("entity.bullet.impact");

	@SubscribeEvent
	public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().registerAll(gun, pistol, smg, double_shotgun, plasma_rifle, carbine, shotgun, sniper, impact, revolver);
	}

	public static SoundEvent initSound(String name) {
		ResourceLocation loc = KaleidiosGuns.rl(name);
		return new SoundEvent(loc).setRegistryName(loc);
	}
}
