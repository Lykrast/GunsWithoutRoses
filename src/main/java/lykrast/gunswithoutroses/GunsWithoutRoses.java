package lykrast.gunswithoutroses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lykrast.gunswithoutroses.registry.GWREnchantments;
import lykrast.gunswithoutroses.registry.GWREntities;
import lykrast.gunswithoutroses.registry.GWRItems;
import lykrast.gunswithoutroses.registry.GWRSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("gunswithoutroses")
public class GunsWithoutRoses {
	public static final String MODID = "gunswithoutroses";
	
	public static final Logger LOGGER = LogManager.getLogger();

	public GunsWithoutRoses() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		GWRItems.REG.register(bus);
		bus.addListener(GWRItems::makeCreativeTab);
		GWREnchantments.REG.register(bus);
		GWREntities.REG.register(bus);
		GWRSounds.REG.register(bus);
	}
	
	public static ResourceLocation rl(String name) {
		return new ResourceLocation(MODID, name);
	}
}
