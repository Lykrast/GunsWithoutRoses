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
	//Fuck it I'm copying Nature's Aura
	//There's a lot of ducktape in there cause it's not how the file looked like originally
	public static EntityType<BulletEntity> BULLET;

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static void regsiterEntities(final RegistryEvent.Register<EntityType<?>> event) {
		IForgeRegistry<EntityType<?>> reg = event.getRegistry();
		reg.register(EntityType.Builder
				.<BulletEntity>of(BulletEntity::new, MobCategory.MISC)
				.sized(0.3125f, 0.3125f).setUpdateInterval(10).setTrackingRange(64).setShouldReceiveVelocityUpdates(true)
				.build(GunsWithoutRoses.MODID + ":bullet").setRegistryName(GunsWithoutRoses.MODID, "bullet"));
		BULLET = (EntityType<BulletEntity>) reg.getValue(GunsWithoutRoses.rl("bullet"));
	}
}
