package lykrast.gunswithoutroses;

import lykrast.gunswithoutroses.entity.BulletEntity;
import lykrast.gunswithoutroses.gui.BulletBagGui;
import lykrast.gunswithoutroses.item.BulletBagItem;
import lykrast.gunswithoutroses.registry.GWREntities;
import lykrast.gunswithoutroses.registry.GWRItems;
import lykrast.gunswithoutroses.registry.GWRMenu;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GunsWithoutRoses.MODID, value = Dist.CLIENT)
public class ClientStuff {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		//Same renderer as potions
    	event.registerEntityRenderer(GWREntities.BULLET.get(), (context) -> new ThrownItemRenderer<BulletEntity>(context));
    }

	@SubscribeEvent
	public static void clientStuff(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> MenuScreens.register(GWRMenu.BAG_CONTAINER, BulletBagGui::new));
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelEvent.RegisterAdditional event) {
		ItemProperties.register(GWRItems.bulletBag.get(), GunsWithoutRoses.rl(BulletBagItem.OPEN),
				(stack, world, entity, someint) -> !stack.isEmpty() && stack.hasTag() && stack.getOrCreateTag().contains(BulletBagItem.OPEN) && stack.getOrCreateTag().getBoolean(BulletBagItem.OPEN) ? 1 : 0);
	}

}
