package lykrast.gunswithoutroses;

import lykrast.gunswithoutroses.item.HungerBulletItem;
import lykrast.gunswithoutroses.registry.ModEntities;
import lykrast.gunswithoutroses.registry.ModItems;
import lykrast.gunswithoutroses.renderer.BulletSpriteRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemModelsProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GunsWithoutRoses.MODID, value = Dist.CLIENT)
public class ClientStuff {

	@SubscribeEvent
	public static void clientStuff(final FMLClientSetupEvent event) {
		//Same renderer as potions
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.BULLET, (manager) -> new BulletSpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));

		ItemModelsProperties.register(ModItems.hungerBullet, GunsWithoutRoses.rl("shot"), (stack, world, entity) -> HungerBulletItem.isShot(stack) ? 1 : 0);
	}

}
