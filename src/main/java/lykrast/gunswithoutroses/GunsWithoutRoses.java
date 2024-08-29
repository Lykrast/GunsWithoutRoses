package lykrast.gunswithoutroses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lykrast.gunswithoutroses.registry.ModEnchantments;
import lykrast.gunswithoutroses.registry.ModEntities;
import lykrast.gunswithoutroses.registry.ModItems;
import lykrast.gunswithoutroses.registry.ModSounds;
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
		ModItems.REG.register(bus);
		bus.addListener(ModItems::makeCreativeTab);
		ModEnchantments.REG.register(bus);
		ModEntities.REG.register(bus);
		ModSounds.REG.register(bus);
	}
	
	public static ResourceLocation rl(String name) {
		return new ResourceLocation(MODID, name);
	}
}
