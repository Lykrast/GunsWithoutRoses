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
			double_shotgunReload = initSound("item.double_shotgun.reload"),
			plasma_rifle = initSound("item.plasma_rifle.shoot"),
			carbine = initSound("item.carbine.shoot"),
			shotgun = initSound("item.shotgun.shoot"),
			sniper = initSound("item.sniper.shoot"),
			revolver = initSound("item.revolver.shoot"),
			revolverReload = initSound("item.revolver.reload"),
			skillShot = initSound("item.skill_shot.shoot"),
			rocketLauncher = initSound("item.rocket_launcher.shoot"),
			witherLauncher = initSound("item.wither_launcher.shoot"),
			impact = initSound("entity.bullet.impact"),
			vampireShotgun = initSound("item.vampire_shotgun.shoot");

	@SubscribeEvent
	public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().registerAll(gun, pistol, smg, double_shotgun, plasma_rifle, carbine, shotgun, sniper, impact, revolver, skillShot, revolverReload, double_shotgunReload, rocketLauncher, witherLauncher, vampireShotgun);
	}

	public static SoundEvent initSound(String name) {
		ResourceLocation loc = KaleidiosGuns.rl(name);
		return new SoundEvent(loc).setRegistryName(loc);
	}
}
