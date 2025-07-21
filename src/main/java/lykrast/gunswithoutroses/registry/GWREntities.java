package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import lykrast.gunswithoutroses.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GWREntities {
	public static RegistryObject<EntityType<BulletEntity>> BULLET;
	public static RegistryObject<EntityType<PiercingBulletEntity>> BULLET_PIERCING;
	public static RegistryObject<EntityType<BouncingBulletEntity>> BULLET_BOUNCING;
	public static final DeferredRegister<EntityType<?>> REG = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GunsWithoutRoses.MODID);

	static {
		BULLET = REG.register("bullet", () -> EntityType.Builder
				.<BulletEntity>of(BulletEntity::new, MobCategory.MISC)
				.sized(0.3125f, 0.3125f).setUpdateInterval(2).setTrackingRange(64).setShouldReceiveVelocityUpdates(true)
				.build(GunsWithoutRoses.MODID + ":bullet"));
		BULLET_PIERCING = REG.register("bullet_piercing", () -> EntityType.Builder
				.<PiercingBulletEntity>of(PiercingBulletEntity::new, MobCategory.MISC)
				.sized(0.3125f, 0.3125f).setUpdateInterval(2).setTrackingRange(64).setShouldReceiveVelocityUpdates(true)
				.build(GunsWithoutRoses.MODID + ":bullet_piercing"));
		BULLET_BOUNCING = REG.register("bullet_bouncing", () -> EntityType.Builder
				.<BouncingBulletEntity>of(BouncingBulletEntity::new, MobCategory.MISC)
				.sized(0.3125f, 0.3125f).setUpdateInterval(2).setTrackingRange(64).setShouldReceiveVelocityUpdates(true)
				.build(GunsWithoutRoses.MODID + ":bullet_bouncing"));
	}
}
