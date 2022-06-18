package lykrast.gunswithoutroses;

import lykrast.gunswithoutroses.entity.BulletEntity;
import lykrast.gunswithoutroses.item.HungerBulletItem;
import lykrast.gunswithoutroses.registry.ModEntities;
import lykrast.gunswithoutroses.registry.ModItems;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GunsWithoutRoses.MODID, value = Dist.CLIENT)
public class ClientStuff {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		//Same renderer as potions
    	event.registerEntityRenderer(ModEntities.BULLET.get(), (context) -> new ThrownItemRenderer<BulletEntity>(context));
    }

	@SubscribeEvent
	public static void clientStuff(final FMLClientSetupEvent event) {
		ItemProperties.register(ModItems.hungerBullet.get(), GunsWithoutRoses.rl("shot"), (stack, world, entity, someint) -> HungerBulletItem.isShot(stack) ? 1 : 0);
	}

}
