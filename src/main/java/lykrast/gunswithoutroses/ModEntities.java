package lykrast.gunswithoutroses;

import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GunsWithoutRoses.MODID)
public class ModEntities {
	//Values from small fireball
	public static final EntityType<BulletEntity> BULLET = EntityType.Builder
			.<BulletEntity>create(BulletEntity::new, EntityClassification.MISC)
			.size(0.3125f, 0.3125f).func_233606_a_(4).func_233608_b_(10)
			.build(GunsWithoutRoses.MODID + ":bullet");

	@SubscribeEvent
	public static void regsiterEntities(final RegistryEvent.Register<EntityType<?>> event) {
		IForgeRegistry<EntityType<?>> reg = event.getRegistry();
		BULLET.setRegistryName(GunsWithoutRoses.MODID, "bullet");
		reg.register(BULLET);
	}
}
