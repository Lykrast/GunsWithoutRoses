package xyz.kaleidiodev.kaleidiosguns.registry;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.kaleidiodev.kaleidiosguns.KaleidiosGuns;
import xyz.kaleidiodev.kaleidiosguns.entity.BulletEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = KaleidiosGuns.MODID)
public class ModEntities {

	public static final EntityType<BulletEntity> BULLET = EntityType.Builder
			.<BulletEntity>of(BulletEntity::new, EntityClassification.MISC)
			.sized(0.3125f, 0.3125f).setUpdateInterval(100).setTrackingRange(64).setShouldReceiveVelocityUpdates(false)
			.build(KaleidiosGuns.MODID + ":bullet");

	@SubscribeEvent
	public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
		IForgeRegistry<EntityType<?>> reg = event.getRegistry();
		BULLET.setRegistryName(KaleidiosGuns.MODID, "bullet");
		reg.register(BULLET);
	}
}
