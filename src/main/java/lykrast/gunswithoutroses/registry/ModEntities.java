package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GunsWithoutRoses.MODID)
public class ModEntities {
	//So it doesn't like doing the build() in the static initializer now...
	public static EntityType<BulletEntity> BULLET;

	@SubscribeEvent
	public static void regsiterEntities(final RegistryEvent.Register<EntityType<?>> event) {
		IForgeRegistry<EntityType<?>> reg = event.getRegistry();
		//So I'll ducktape it here by just moving the initializer and making it not final
		BULLET = EntityType.Builder
				.<BulletEntity>of(BulletEntity::new, MobCategory.MISC)
				.sized(0.3125f, 0.3125f).setUpdateInterval(10).setTrackingRange(64).setShouldReceiveVelocityUpdates(true)
				.build(GunsWithoutRoses.MODID + ":bullet");
		BULLET.setRegistryName(GunsWithoutRoses.MODID, "bullet");
	}
}
