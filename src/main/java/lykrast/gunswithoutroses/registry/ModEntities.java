package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GunsWithoutRoses.MODID)
public class ModEntities {

	public static final EntityType<BulletEntity> BULLET = EntityType.Builder
			.<BulletEntity>of(BulletEntity::new, EntityClassification.MISC)
			.sized(0.3125f, 0.3125f).setUpdateInterval(10).setTrackingRange(64).setShouldReceiveVelocityUpdates(false)
			.build(GunsWithoutRoses.MODID + ":bullet");

	@SubscribeEvent
	public static void regsiterEntities(final RegistryEvent.Register<EntityType<?>> event) {
		IForgeRegistry<EntityType<?>> reg = event.getRegistry();
		BULLET.setRegistryName(GunsWithoutRoses.MODID, "bullet");
		reg.register(BULLET);
	}
}
