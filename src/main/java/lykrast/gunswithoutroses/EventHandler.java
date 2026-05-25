package lykrast.gunswithoutroses;

import lykrast.gunswithoutroses.config.GWRConfigValues;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GunsWithoutRoses.MODID)
public class EventHandler {
	//ok so that's kind of a bad bandaid (instead of doing a mixin) so here's the idea:
	//those 2 events are fired from the same function and only on server, so hurting is processed in between the 2 calls
	//thus if I memorize the entity that was just hurt I know the damage must come from that previous event
	//I do that because the knockback event does not have the context of the damage source (aaaaaaaaa)
	//also check the tick count just in case somehow a knockback happens without a damageevent
	private static LivingEntity lastGunned = null;
	private static int gunnedTick = -1;
	public static final TagKey<DamageType> IS_BULLET = TagKey.create(Registries.DAMAGE_TYPE, GunsWithoutRoses.rl("is_bullet"));
	
	@SubscribeEvent
	public static void entityDamage(final LivingDamageEvent event) {
		if (event.getSource().is(IS_BULLET)) {
			lastGunned = event.getEntity();
			gunnedTick = lastGunned.tickCount;
		}
		else {
			lastGunned = null;
		}
	}

	@SubscribeEvent
	public static void entityKnockback(final LivingKnockBackEvent event) {
		if (lastGunned != null) {
			LivingEntity target = event.getEntity();
			if (target == lastGunned && target.tickCount == gunnedTick) {
				event.setStrength(event.getStrength() * GWRConfigValues.GUN_KNOCKBACK);
			}
		}
	}
}
