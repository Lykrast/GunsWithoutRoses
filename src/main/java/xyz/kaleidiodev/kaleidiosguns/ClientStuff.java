package xyz.kaleidiodev.kaleidiosguns;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemModelsProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xyz.kaleidiodev.kaleidiosguns.item.HungerBulletItem;
import xyz.kaleidiodev.kaleidiosguns.item.XPBulletItem;
import xyz.kaleidiodev.kaleidiosguns.registry.ModEntities;
import xyz.kaleidiodev.kaleidiosguns.registry.ModItems;
import xyz.kaleidiodev.kaleidiosguns.renderer.BulletSpriteRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = KaleidiosGuns.MODID, value = Dist.CLIENT)
public class ClientStuff {

	@SubscribeEvent
	public static void clientStuff(final FMLClientSetupEvent event) {
		//Same renderer as potions
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.BULLET, (manager) -> new BulletSpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));

		ItemModelsProperties.register(ModItems.hungerBullet, KaleidiosGuns.rl("shot"), (stack, world, entity) -> HungerBulletItem.isShot(stack) ? 1 : 0);
		ItemModelsProperties.register(ModItems.xpBullet, KaleidiosGuns.rl("shot"), (stack, world, entity) -> XPBulletItem.isShot(stack) ? 1 : 0);
	}

}
