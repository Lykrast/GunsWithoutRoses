package lykrast.gunswithoutroses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lykrast.gunswithoutroses.config.GWRConfig;
import lykrast.gunswithoutroses.config.GWRConfigValues;
import lykrast.gunswithoutroses.registry.GWRAttributes;
import lykrast.gunswithoutroses.registry.GWREffects;
import lykrast.gunswithoutroses.registry.GWREnchantments;
import lykrast.gunswithoutroses.registry.GWREntities;
import lykrast.gunswithoutroses.registry.GWRItems;
import lykrast.gunswithoutroses.registry.GWRMenu;
import lykrast.gunswithoutroses.registry.GWRSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
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
		GWRAttributes.REG.register(bus);
		bus.addListener(GWRAttributes::playerAttributes);
		GWREffects.REG.register(bus);
		GWRMenu.REG.register(bus);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GWRConfig.COMMON_SPEC);
		bus.addListener(this::onModConfigEvent);
	}
	
	public static ResourceLocation rl(String name) {
		return new ResourceLocation(MODID, name);
	}

	public void onModConfigEvent(final ModConfigEvent event) {
		ModConfig config = event.getConfig();
		// Recalculate the configs when they change
		if (config.getSpec() == GWRConfig.COMMON_SPEC) GWRConfigValues.refresh(config);
	}
}
